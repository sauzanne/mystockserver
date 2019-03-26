package fr.mystocks.mystockserver.service.finance.stockprice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import fr.mystocks.mystockserver.data.finance.place.Place;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPriceId;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.service.finance.stockprice.constant.YahooFinanceEnum;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.date.DateFinancialTools;
import fr.mystocks.mystockserver.technic.date.DateTools;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.http.HttpTools;
import fr.mystocks.mystockserver.technic.http.HttpTools.HttpToolsRunnable;
import fr.mystocks.mystockserver.technic.util.DoubleReturnValue;

@Service("yahooFinanceService")
public class YahooFinanceServiceImpl implements StockPriceService {

	private String crumb;

	private String cookie;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String DEFAULT_URL = "https://fr.finance.yahoo.com/quote/";

	private static final String DEFAULT_TICKER = "AAPL";

	private static final String SET_COOKIE = "set-cookie";

	private static final String US_PLACE = "US";

	private static final String NASDAQ_PLACE = "GS";

	/* l'heure utilisée par le serveur de yahoo */
	private static final String ATLANTIC_STANDARD_TIME = "-04:00";

	@Override
	public StockPrice getLast(StockTicker st) {
		try {
			LocalDate startDate = DateTools.getLastOpenDate(LocalDate.now());

			List<StockPrice> listStockPrice = new ArrayList<>();

			listStockPrice = getPriceForPeriod(st, startDate, startDate);

			/* 2ème essai avec la date précédente */
			if (listStockPrice == null || listStockPrice.isEmpty()) {
				startDate = DateTools.getPreviousOpenDate(startDate);
				listStockPrice = getPriceForPeriod(st, startDate, startDate);
			}

			if (listStockPrice != null && !listStockPrice.isEmpty()) {
				return listStockPrice.get(0);
			} else { /*
						 * si on ne trouve pas de quotation on cherche sur 1 an glissant
						 */
				LocalDate oneYearBefore = startDate.minusYears(1);
				listStockPrice = getPriceForPeriod(st, oneYearBefore, startDate);

				/*
				 * on recherche ensuite la date la plus proche dans les résultats
				 */
				if (listStockPrice != null && !listStockPrice.isEmpty()) {
					startDate = DateTools.getPreviousOpenDate(startDate);
					while (startDate.isAfter(oneYearBefore) || startDate.isEqual(oneYearBefore)) {
						StockPrice result = DateFinancialTools.getStockPriceFromDate(listStockPrice, startDate);

						if (result != null) {
							return result;
						}
						startDate = DateTools.getPreviousOpenDate(startDate);
					}
				}

			}
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);

		}
		return null;
	}

	@Override
	public List<StockPrice> getPriceForPeriod(StockTicker st, LocalDate start, LocalDate end, Boolean... repeat) {
		List<StockPrice> list = Lists.newArrayList();

		try {

			provideCookie();
			String url = generateURL(st, start, end);

			DoubleReturnValue<String, String>[] headers = generateDefaultHeaders(st);

			String result = HttpTools.getURLWithHeaders(url, headers);
			list = generateQuote(result);
		} catch (IllegalArgumentException e) {
			/* on ne rappelle qu'une fois au maximum */
			if (repeat.length == 0) {
				/* dans ce cas cela signifie probablement que le crumb est faux */
				ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
				/* on réinitialise pour le prochain appel et on rappelle */
				this.cookie = null;
				return getPriceForPeriod(st, start, end, new Boolean[] { false });
			}
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		} catch (ClientProtocolException e) {
			ExceptionTools.processException(this, logger, e);
		} catch (IOException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return list;
	}

	private DoubleReturnValue<String, String>[] generateDefaultHeaders(StockTicker st) {
		@SuppressWarnings("unchecked")
		DoubleReturnValue<String, String>[] headers = new DoubleReturnValue[3];
		headers[0] = new DoubleReturnValue<String, String>(HttpTools.COOKIE_HEADER, this.cookie);
		headers[1] = new DoubleReturnValue<String, String>(HttpTools.REFERER_HEADER, generateRefererURL(st));
		headers[2] = new DoubleReturnValue<String, String>(HttpTools.USER_AGENT_HEADER, HttpTools.USER_AGENT_CHROME);
		return headers;
	}

	private List<StockPrice> generateQuote(String result) {
		String separatorLine = YahooFinanceEnum.SEPLINE.label;
		String separatorCol = YahooFinanceEnum.SEPCOL.label;
		int nbLine = result.split(separatorLine).length;

		String[] line = new String[nbLine];
		/**
		 * the result need to have a minimum of 2 line to be signifiant (there is a
		 * headline)
		 */
		if (nbLine > 1) {
			StockPrice[] stockPrices = new StockPrice[line.length - 1];
			for (int i = 1; i < nbLine; i++) {
				line[i] = result.split(separatorLine)[i];

				int nbCol = line[i].split(separatorCol).length;
				String[] cols = new String[nbCol];

				stockPrices[i - 1] = new StockPrice();

				for (int j = 0; j < line[i].split(separatorCol).length; j++) {
					cols[j] = line[i].split(separatorCol)[j];

					/**
					 * j=0 => date
					 */
					if (j == 0) {

						String strDate[] = cols[j].split(YahooFinanceEnum.SEPDATE.label);
						/**
						 * Yahoo register is month from 1 to 12, Java from 0 to 11
						 */

						LocalDate priceDate = LocalDate.of(Integer.valueOf(strDate[0]),
								Month.of(Integer.valueOf(strDate[1])), Integer.valueOf(strDate[2]));

						if (stockPrices[i - 1].getStockPriceId() == null) {
							stockPrices[i - 1].setStockPriceId(new StockPriceId());
						}

						stockPrices[i - 1].getStockPriceId().setInputDate(priceDate);
					}
					/**
					 * j=4 => the quote
					 */
					else if (j == 4) {
						if (!cols[j].equals(TechnicalConstant.NULL)) {
							stockPrices[i - 1].setPrice(new BigDecimal(cols[j]));
							stockPrices[i - 1].setClose(false);
							/**
							 * last interesting date we leave the loop
							 */
							break;
						}
					}

				}
			}
			return Arrays.asList(stockPrices);
		}
		return null;
	}

	private String generateURL(StockTicker st, LocalDate start, LocalDate end) {
		String url = "";

		/*
		 * URL example :
		 * https://query1.finance.yahoo.com/v7/finance/download/UBI.PA?period1=
		 * 1483225200&period2=1496268000&interval=1d&events=history&crumb= KvFonlsRtB2
		 */

		url = YahooFinanceEnum.URL.label;

		Stock stock = Optional.ofNullable(st.getStock()).orElse(null);

		// pour les marchés US yahoo ne nécessite pas l'extension pays
		if (!st.getPlace().getCode().equals(NASDAQ_PLACE) && !st.getPlace().getCode().equals(US_PLACE) && stock != null
				&& !stock.getStockType().getCode().equals("stocks.type.index")) {
			try {
				url += URLEncoder.encode(st.getCode() + "." + st.getPlace().getCode(), TechnicalConstant.ENCODING);
			} catch (UnsupportedEncodingException e) {
				ExceptionTools.processException(this, logger, e);
			}
		} else {
			try {
				url += URLEncoder.encode(st.getCode(), TechnicalConstant.ENCODING);
			} catch (UnsupportedEncodingException e) {
				ExceptionTools.processException(this, logger, e);
			}
		}
		url += TechnicalConstant.QUESTION;
		url += YahooFinanceEnum.PERIOD1.label + "="
				+ new Long(start.atStartOfDay().toEpochSecond(ZoneOffset.of(ATLANTIC_STANDARD_TIME)))
				+ TechnicalConstant.AMPERSAND_SEPARATOR;
		// il est obligatoire de rajouter 1j si la date est à 00:00 : exemple avec
		// start=28/12/2017 and end=28/12/2017 on recherche un cours entre le 28/12/2017
		// 00:00 et le 29/12/2017 00:00
		url += YahooFinanceEnum.PERIOD2.label + "="
				+ new Long(end.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.of(ATLANTIC_STANDARD_TIME)))
				+ TechnicalConstant.AMPERSAND_SEPARATOR;
		url += YahooFinanceEnum.INTERVAL.label + "=1d&";
		url += YahooFinanceEnum.EVENTS.label + "=history&";
		url += YahooFinanceEnum.CRUMB.label + TechnicalConstant.EQUALS + crumb;

		return url;
	}

	private String generateRefererURL(StockTicker st) {
		String url = DEFAULT_URL;

		// exemple d'URL à générér :
		// https://finance.yahoo.com/quote/UBI.PA/history?p=UBI.PA

		String ticker = null;
		// pour les marchés US yahoo ne nécessite pas l'extension pays
		if (!st.getPlace().getCode().equals("GS") && !st.getPlace().getCode().equals(US_PLACE)) {
			ticker = st.getCode() + "." + st.getPlace().getCode();
			url += ticker;
		} else {
			ticker = st.getCode();
			url += ticker;
		}
		url += "/" + YahooFinanceEnum.HISTORY.label;
		url += TechnicalConstant.QUESTION + YahooFinanceEnum.P.label + ticker;

		return url;
	}

	@Override
	public boolean checkStocks(StockTicker st) {
		try {
			return getLast(st) != null;
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return false;
	}

	private final class YahooCookiesRunnable implements HttpToolsRunnable {

		@Override
		public StringBuilder myRun(HttpResponse response2) throws IOException {
			StringBuilder sb = new StringBuilder();

			Header[] headers = response2.getAllHeaders();

			for (Header h : headers) {
				if (h.getName().toLowerCase().equals(SET_COOKIE)) {
					for (HeaderElement he : h.getElements()) {
						if (he.getName().equals(YahooFinanceEnum.COOKIES_NAME.label)) {
							sb.append(he.getName() + TechnicalConstant.EQUALS + he.getValue());
							break;
						}
					}
				}
			}

			sb.append(TechnicalConstant.LINE_SEPARATOR);

			HttpEntity entity2 = response2.getEntity();
			InputStream is = null;

			try {

				is = entity2.getContent();

				BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, TechnicalConstant.ENCODING));

				String line;

				StringBuilder htmlContent = new StringBuilder();

				while ((line = bufReader.readLine()) != null) {
					htmlContent.append(line);
					htmlContent.append(TechnicalConstant.LINE_SEPARATOR);
				}

				String cutAtCrumbStore = htmlContent.toString().split("CrumbStore\":")[1];

				sb.append((cutAtCrumbStore.substring(0, cutAtCrumbStore.indexOf(TechnicalConstant.COMMA))).split(":")[1]
						.replaceAll("\"", "").replaceAll(TechnicalConstant.BRACE_RIGHT, ""));

			} finally {
				if (is != null) {
					is.close();
				}
			}
			return sb;
		}

	}

	@Override
	public BigDecimal getAveragePrice(StockTicker st, LocalDate start, LocalDate end) {
		try {
			List<StockPrice> prices = getPriceForPeriod(st, start, end);

			if (!prices.isEmpty()) {
				BigDecimal sum = prices.stream().map(StockPrice::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

				return sum.divide(BigDecimal.valueOf(prices.size()), MathContext.DECIMAL128);
			}

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

	private void initYahooService() throws ClientProtocolException, IOException {
		StockTicker defaultStockTicker = new StockTicker(DEFAULT_TICKER, new Place(US_PLACE));
		@SuppressWarnings("unchecked")
		DoubleReturnValue<String, String>[] headers = new DoubleReturnValue[2];
		headers[0] = new DoubleReturnValue<String, String>(HttpTools.REFERER_HEADER,
				generateRefererURL(defaultStockTicker));
		headers[1] = new DoubleReturnValue<String, String>(HttpTools.USER_AGENT_HEADER, HttpTools.USER_AGENT_CHROME);

		StringBuilder yahooData = HttpTools.apacheHttpGetEncapsulation(DEFAULT_URL + DEFAULT_TICKER,
				new YahooCookiesRunnable(), headers);
		String[] cookieAndCrub = yahooData.toString().split(TechnicalConstant.LINE_SEPARATOR);

		this.cookie = StringEscapeUtils.unescapeJava(cookieAndCrub[0].trim());
		this.crumb = StringEscapeUtils.unescapeJava(cookieAndCrub[1].trim());
		UrlValidator urlValidator = new UrlValidator();
		logger.debug("Yahoo cookie " + cookie + " Yahoo crumb " + crumb);

		/* si l'url n'est pas vaide on relance la validation */
		if (!urlValidator.isValid(DEFAULT_URL + TechnicalConstant.QUESTION + crumb)) {
			initYahooService();
		}

	}

	private void provideCookie() throws ClientProtocolException, IOException {
		if (Strings.isNullOrEmpty(cookie)) {
			initYahooService();
		}
	}

}
