package fr.mystocks.mystockserver.service.finance.performance;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.review.ReviewDao;
import fr.mystocks.mystockserver.data.finance.currency.Currency;
import fr.mystocks.mystockserver.data.finance.operations.Operations;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;
import fr.mystocks.mystockserver.service.finance.currency.CurrencyService;
import fr.mystocks.mystockserver.service.finance.currency.Price;
import fr.mystocks.mystockserver.service.finance.stockprice.StockPriceService;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;
import fr.mystocks.mystockserver.technic.util.DoubleReturnValue;

@Transactional
@Service("valuationService")
public class ValuationServiceImpl implements ValuationService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PropertiesTools propertiesTools;

	@Autowired
	private ReviewDao reviewDao;

	@Autowired
	private StockPriceService stockPriceService;

	@Autowired
	private CurrencyService currencyService;

	/**
	 * Récupère le taux de change entre le rapport et la capitalisation
	 * 
	 * @param currencyMarketPlace la monnaie de la place de capitalisation
	 * @param currencyReview      la monnaie du rapport
	 * @return le taux de change si il peut être calculé
	 */
	private BigDecimal getExchangeRate(Currency currencyMarketPlace, Currency currencyReview) {
		BigDecimal exchangeRate = BigDecimal.ONE;
		if (!currencyMarketPlace.getAlpha3().equals(currencyReview.getAlpha3())) {

			Price priceExchangeRate = null;
			try {
				priceExchangeRate = currencyService.getCurrentFxRate(currencyMarketPlace.getAlpha3(),
						currencyReview.getAlpha3());

				exchangeRate = priceExchangeRate.getPrice();

			} catch (Exception e) {
				ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
			}
		}
		return exchangeRate;
	}

	@Override
	public BigDecimal getMarketCap(StockPrice price, BigInteger numberShares, Currency currencyMarketPlace,
			Currency currencyReview) {
		Optional<BigDecimal> optionalPrice = Optional.ofNullable(price.getPrice());
		BigInteger nbShares = Optional.ofNullable(numberShares)
				.orElseThrow(() -> new FunctionalException(this, "error.finance.performance.numbershares",
						new String[] { propertiesTools.getProperty("error.finance.performance.capitalization") }));

		BigDecimal exchangeRate = getExchangeRate(currencyMarketPlace, currencyReview);

		return optionalPrice
				.orElseThrow(() -> new FunctionalException(this, "error.finance.performance.price",
						new String[] { propertiesTools.getProperty("error.finance.performance.capitalization") }))
				.multiply(new BigDecimal(nbShares), DEFAULT_PRECISION)
				.multiply(Optional.ofNullable(exchangeRate).orElse(BigDecimal.ONE), DEFAULT_PRECISION);

	}

	@Override
	public BigDecimal getPriceEarning(StockPrice price, BigInteger numberShares, BigDecimal earnings, PeriodEnum period,
			Currency currencyMarketPlace, Currency currencyReview) {
		BigDecimal marketCap = getMarketCap(price, numberShares, currencyMarketPlace, currencyReview);

		Optional<BigDecimal> optionalEarnings = Optional.ofNullable(earnings);

		Optional<PeriodEnum> optionalPeriod = Optional.ofNullable(period);

		return marketCap.divide(
				optionalEarnings.orElseThrow(() -> new FunctionalException(this, "error.finance.performance.required",
						new String[] { getProperty("error.finance.performance.earnings"),
								getProperty("error.finance.performance.pe") })),
				DEFAULT_PRECISION).divide(
						new BigDecimal(
								optionalPeriod
										.orElseThrow(() -> new FunctionalException(this,
												"error.finance.performance.required",
												new String[] { getProperty("error.finance.performance.period"),
														getProperty("error.finance.performance.pe") }))
										.getDivideFactor()),
						DEFAULT_PRECISION);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public DoubleReturnValue<BigDecimal, Review> getPriceEarnings(StockTicker st) {
		try {
			List<Review> listReview = reviewDao.findReview(st.getStock().getId(), null, null, null, null);

			Set<Review> setReview = new HashSet<>();

			if (listReview != null && !listReview.isEmpty()) {

				Integer maxStartYear = null;

				/*
				 * on ne sélectionne que la période la plus proche avec au moins un résultat de
				 * fourni
				 */
				for (Review r : listReview) {
					Integer startYear = r.getStartYear();
					Operations operations = Optional.ofNullable(r.getOperations()).orElse(new Operations());

					BigDecimal earnings = Optional.ofNullable(operations.getAdjustedEarnings())
							.orElse(operations.getShareownersEarnings());

					if ((maxStartYear == null || startYear > maxStartYear) && earnings != null) {
						maxStartYear = startYear;
						setReview.clear();
						setReview.add(r);
					} else if (startYear == maxStartYear && earnings != null) {
						setReview.add(r);
					}
				}

				if (setReview.isEmpty()) {
					throw new FunctionalException(this, "error.finance.performance.required",
							new String[] { getProperty("error.finance.performance.earnings"),
									getProperty("error.finance.performance.pe") });
				}

				Review myReview = findMostRelevantReview(setReview);

//				Operations operations = Optional.ofNullable(myReview.getOperations())
//						.orElseThrow(() -> new FunctionalException(this, "error.finance.performance.required",
//								new String[] { getProperty("error.finance.performance.operations"),
//										getProperty("error.finance.performance.pe") }));

				BigDecimal pe = getPriceEarning(
						Optional.ofNullable(stockPriceService.getLast(st))
								.orElseThrow(() -> new FunctionalException(this, "error.finance.performance.price",
										new String[] { getProperty("error.finance.performance.pe") })),
						myReview.getNbSharesEndPeriod(),
						Optional.ofNullable(myReview.getOperations().getAdjustedEarnings())
								.orElse(myReview.getOperations().getShareownersEarnings()),
						PeriodEnum.valueOf(myReview.getPeriod()), st.getPlace().getCurrency(), myReview.getCurrency());
				return new DoubleReturnValue<BigDecimal, Review>(pe, myReview);

			} else {
				throw new FunctionalException(this, "error.finance.review.notexist");
			}

		} catch (FunctionalException e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	private Review findMostRelevantReview(Set<Review> setReview) {
		Review mostRelevantReview = null;
		for (Review r : setReview) {
			if (mostRelevantReview == null) {
				mostRelevantReview = r;
			} else if (r.getAccount().getAccountType().getId() > mostRelevantReview.getAccount().getAccountType()
					.getId()) {
				mostRelevantReview = r;
			}
		}
		return mostRelevantReview;
	}

	/**
	 * Simplication of property access
	 * 
	 * @param key the key
	 * @return text of the property
	 */
	private String getProperty(String key) {
		return propertiesTools.getProperty(key);
	}

}
