package fr.mystocks.mystockserver.service.finance.currency;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.Map.Entry;
import java.util.Set;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.config.units.EntryUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import fr.mystocks.mystockserver.technic.date.DateTools;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.http.HttpTools;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;

@Service("currencyService")
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String ALPHA_VANTAGE_KEY = "39UXMNKEV61CK3YM";
	private static final String FROM_CURRENCY = "from_currency";
	private static final String TO_CURRENCY = "to_currency";
	private static final String API_KEY = "apikey";
	private static final String FUNCTION_CURRENCY = "function=CURRENCY_EXCHANGE_RATE";
	private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query?";

	@Autowired
	private PropertiesTools propertiesTools;

	
    private UserManagedCache<String, Price> fxCache = UserManagedCacheBuilder
    	    .newUserManagedCacheBuilder(String.class, Price.class)
    	    .withResourcePools(ResourcePoolsBuilder.newResourcePoolsBuilder().heap(1000L, EntryUnit.ENTRIES))
    	    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofDays(1))).build(true);


	@Override
	public final Price getCurrentFxRate(String base, String counterPart) {
		try {
			String pair = base + counterPart;
			Price priceInCache = fxCache.get(pair);

			if (priceInCache != null) {
				return priceInCache;
			}

			logger.error("Call alphavantage.co for pair" + base + "/" + counterPart);

			String jsonResponse = HttpTools
					.getURLWithHeaders(ALPHA_VANTAGE_URL + FUNCTION_CURRENCY + "&" + FROM_CURRENCY + "=" + base + "&"
							+ TO_CURRENCY + "=" + counterPart + "&" + API_KEY + "=" + ALPHA_VANTAGE_KEY, null);

			GsonBuilder gsonBuilder = new GsonBuilder();

			gsonBuilder.registerTypeAdapter(Price.class, new CustomPriceDeserializer());
			Gson gson = gsonBuilder.create();

			Price price = gson.fromJson(jsonResponse, Price.class);

			fxCache.put(pair, price);

			return price;

		} catch (Exception e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	private class CustomPriceDeserializer implements JsonDeserializer<Price> {

		@Override
		public Price deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)
				throws JsonParseException {

			Price response = new Price();

			Set<Entry<String, JsonElement>> enteries = json.getAsJsonObject().entrySet();

			for (Entry<String, JsonElement> entry : enteries) {
				JsonElement jsonValue = (JsonElement) entry.getValue();
				String key = entry.getKey();

				if (key.equals("Realtime Currency Exchange Rate")) {
					for (Entry<String, JsonElement> entryRates : jsonValue.getAsJsonObject().entrySet()) {
						if (entryRates.getKey().equals("5. Exchange Rate")) {
							response.setPrice(entryRates.getValue().getAsBigDecimal());
						} else if (entryRates.getKey().equals("6. Last Refreshed")) {
							response.setInputDate(
									DateTools.convertIsoLocalDateTime(entryRates.getValue().getAsString(), "UTC"));

						}
					}

				}
			}
			return response;
		}
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
