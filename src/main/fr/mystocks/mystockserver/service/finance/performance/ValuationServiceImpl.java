package fr.mystocks.mystockserver.service.finance.performance;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;

@Transactional
@Service("valuationService")
public class ValuationServiceImpl implements ValuationService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PropertiesTools propertiesTools;

	@Override
	public BigDecimal getMarketCap(StockPrice price, BigInteger numberShares) {
		Optional<BigDecimal> optionalPrice = Optional.ofNullable(price.getPrice());
		BigInteger nbShares = Optional.ofNullable(numberShares)
				.orElseThrow(() -> new FunctionalException(this, "error.finance.performance.numbershares",
						new String[] { propertiesTools.getProperty("error.finance.performance.capitalization") }));

		return optionalPrice
				.orElseThrow(() -> new FunctionalException(this, "error.finance.performance.price",
						new String[] { propertiesTools.getProperty("error.finance.performance.capitalization") }))
				.multiply(new BigDecimal(nbShares), DEFAULT_PRECISION);

	}

	@Override
	public BigDecimal getPriceEarning(StockPrice price, BigInteger numberShares, BigDecimal earnings, PeriodEnum period) {
		BigDecimal marketCap = getMarketCap(price, numberShares);

		Optional<BigDecimal> optionalEarnings = Optional.ofNullable(earnings);

		Optional<PeriodEnum> optionalPeriod = Optional.ofNullable(period);

		return marketCap
				.multiply(
						optionalEarnings
								.orElseThrow(
										() -> new FunctionalException(this, "error.finance.performance.earnings",
												new String[] { getProperty("error.finance.performance.earnings"),
														getProperty("error.finance.performance.pe") })),
						DEFAULT_PRECISION)
				.divide(new BigDecimal(optionalPeriod
						.orElseThrow(() -> new FunctionalException(this, "error.finance.performance.earnings",
								new String[] { getProperty("error.finance.performance.period"),
										getProperty("error.finance.performance.pe") }))
						.getNbDays()), DEFAULT_PRECISION);
	}

	/**
	 * Simplication of property access
	 * 
	 * @param key
	 *            the key
	 * @return text of the property
	 */
	private String getProperty(String key) {
		return propertiesTools.getProperty(key);
	}

}
