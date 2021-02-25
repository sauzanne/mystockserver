package fr.mystocks.mystockserver.service.finance.amf;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import fr.mystocks.mystockserver.dao.finance.stock.StockDao;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.date.DateTools;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.http.HttpTools;
import fr.mystocks.mystockserver.technic.mail.MailTools;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;

@Service("amfService")
@Transactional
public class AmfServiceImpl implements AmfService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String AMF_URL = "https://bdif.amf-france.org/technique/bdif-societes";
	private static final String LIMIT = "limit";
	private static final String Q = "q";
	private static final String TIMESTAMP = "timestamp";

	@Autowired
	private PropertiesTools propertiesTools;

	@Autowired
	private StockDao<Stock> stockDao;

	@Autowired
	private MailTools mailTools;

	private final static String MAIL_ADMIN = "sauzanne@gmail.com";

	@Override
	public String getCodeAmf(Stock stock) {
		int limit = 1000;
		try {
			String jsonResponse = HttpTools.getURLWithHeaders(AMF_URL + TechnicalConstant.QUESTION + Q
					+ TechnicalConstant.EQUALS + URLEncoder.encode(stock.getName(), TechnicalConstant.ENCODING) + TechnicalConstant.AMPERSAND_SEPARATOR + LIMIT
					+ TechnicalConstant.EQUALS + limit + TechnicalConstant.AMPERSAND_SEPARATOR + TIMESTAMP
					+ TechnicalConstant.EQUALS + DateTools.getEpoch(LocalDateTime.now()), null);

			if (!Strings.isNullOrEmpty(jsonResponse)) {
				String cleanResponse = StringEscapeUtils.unescapeJava(jsonResponse.trim());
				List<String> reponses = Arrays.asList(cleanResponse.split("\\n")).stream()
						.filter(r -> !r.trim().isEmpty()).collect(Collectors.toList());
				
				if(reponses.size() > 1) {
					logger.error("WARNING : multiple response for stock : "+stock.getName()+"("+stock.getIsin()+")");
				}

				String[] amfCode = reponses.get(0).split("\\|");

				return amfCode[1].trim();
			}

		} catch (Exception e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	@Override
	public String getResult(String valueName, String jetonSociete) {
		try {
			String jsonResponse = HttpTools.getURLWithHeaders(
					"https://bdif.amf-france.org/Resultat-de-recherche-BDIF?formId=BDIF&DOC_TYPE=BDIF&LANGUAGE=fr&subFormId=as&BDIF_RAISON_SOCIALE="
							+ URLEncoder.encode(valueName, "UTF-8") + "&bdifJetonSociete=" + jetonSociete
							+ "&DATE_PUBLICATION=&DATE_OBSOLESCENCE=&valid_form=Lancer+la+recherche&isSearch=true",
					null);

			Document jsoupDocument = Jsoup.parse(jsonResponse);

			Elements links = jsoupDocument.select("a[href]");

			List<String> listLink = links.stream()
					.filter(l -> l.attr("href").startsWith("/technique/proxy-lien?docId=")).map(l -> l.attr("href"))
					.collect(Collectors.toList());

//        for(Element elem : links) {
//        	
//        	String url = elem.attr("href");
//        	
//        	if(url.startsWith("/technique/proxy-lien?docId=")) {
//        		System.out.println(url);
//        		
//        	}
//        }

			// links.stream().filter(e -> e.attr("href").)

			System.out.println(jsonResponse);

		} catch (Exception e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

	@Scheduled(cron = "${cron.measurecalculation}")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void cronAmfUpdate() {
		double acceptableErrorRate = 0.02;
		logger.error("Amf update started at " + LocalDateTime.now() + " acceptable error rate fixed at "
				+ acceptableErrorRate * 100 + " %");
		LocalDate calculationDate = DateTools.getPreviousOpenDate(LocalDate.now());

		// étape 1 : recherche et mise à jour des actions

		// on recherche que les valeurs "FR"
		List<Stock> listStockFR = stockDao.findByIsin("FR");

		// on prend la liste des valeurs à 'updater'
		List<Stock> listStockToUpdate = listStockFR.stream().filter(s -> s.getAmfCode() == null)
				.collect(Collectors.toList());

		for (Stock stock : listStockToUpdate) {
			try {
				String codeAmf = getCodeAmf(stock);

				if (codeAmf != null) {
					stock.setLastModified(LocalDateTime.now());
					stock.setAmfCode(codeAmf);
					stockDao.update(stock);
				} else {
					logger.error("Impossible to get AMF code for " + stock.getIsin() + " / " + stock.getName());
				}
			} catch (Exception e) {
				ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
			}
		}

		String amfStats = "\nAmf update ended at " + LocalDateTime.now();

		mailTools.sendMessage(MAIL_ADMIN, "Amd update ended at " + LocalDateTime.now() + " with success", amfStats);

		logger.error(amfStats);

	}

//    private UserManagedCache<String, Price> fxCache = UserManagedCacheBuilder
//    	    .newUserManagedCacheBuilder(String.class, Price.class)
//    	    .withResourcePools(ResourcePoolsBuilder.newResourcePoolsBuilder().heap(1000L, EntryUnit.ENTRIES))
//    	    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofDays(1))).build(true);
//
//
//	@Override
//	public final Price getCurrentFxRate(String base, String counterPart) {
//		try {
//			String pair = base + counterPart;
//			Price priceInCache = fxCache.get(pair);
//
//			if (priceInCache != null) {
//				return priceInCache;
//			}
//
//			logger.error("Call alphavantage.co for pair" + base + "/" + counterPart);
//
//			String jsonResponse = HttpTools
//					.getURLWithHeaders(ALPHA_VANTAGE_URL + FUNCTION_CURRENCY + "&" + FROM_CURRENCY + "=" + base + "&"
//							+ TO_CURRENCY + "=" + counterPart + "&" + API_KEY + "=" + ALPHA_VANTAGE_KEY, null);
//
//			GsonBuilder gsonBuilder = new GsonBuilder();
//
//			gsonBuilder.registerTypeAdapter(Price.class, new CustomPriceDeserializer());
//			Gson gson = gsonBuilder.create();
//
//			Price price = gson.fromJson(jsonResponse, Price.class);
//
//			fxCache.put(pair, price);
//
//			return price;
//
//		} catch (Exception e) {
//			ExceptionTools.processException(this, logger, e);
//		}
//		return null;
//	}
//
//	private class CustomPriceDeserializer implements JsonDeserializer<Price> {
//
//		@Override
//		public Price deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)
//				throws JsonParseException {
//
//			Price response = new Price();
//
//			Set<Entry<String, JsonElement>> enteries = json.getAsJsonObject().entrySet();
//
//			for (Entry<String, JsonElement> entry : enteries) {
//				JsonElement jsonValue = (JsonElement) entry.getValue();
//				String key = entry.getKey();
//
//				if (key.equals("Realtime Currency Exchange Rate")) {
//					for (Entry<String, JsonElement> entryRates : jsonValue.getAsJsonObject().entrySet()) {
//						if (entryRates.getKey().equals("5. Exchange Rate")) {
//							response.setPrice(entryRates.getValue().getAsBigDecimal());
//						} else if (entryRates.getKey().equals("6. Last Refreshed")) {
//							response.setInputDate(
//									DateTools.convertIsoLocalDateTime(entryRates.getValue().getAsString(), "UTC"));
//
//						}
//					}
//
//				}
//			}
//			return response;
//		}
//	}
//
//	/**
//	 * Simplication of property access
//	 * 
//	 * @param key the key
//	 * @return text of the property
//	 */
//	private String getProperty(String key) {
//		return propertiesTools.getProperty(key);
//	}

}
