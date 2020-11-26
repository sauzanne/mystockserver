package fr.mystocks.mystockserver.service.finance.performance;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import fr.mystocks.mystockserver.data.finance.currency.Currency;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;
import fr.mystocks.mystockserver.technic.util.DoubleReturnValue;

public interface ValuationService {

	final static MathContext DEFAULT_PRECISION = MathContext.DECIMAL128;

	BigDecimal getMarketCap(StockPrice price, BigInteger numberShares, Currency currencyMarketPlace, Currency currencyReview);

	BigDecimal getPriceEarning(StockPrice price, BigInteger numberShares, BigDecimal earnings,
			PeriodEnum period, Currency currencyMarketPlace, Currency currencyReview);

	DoubleReturnValue<BigDecimal, Review> getPriceEarnings(StockTicker st, StockPrice last);

	DoubleReturnValue<BigDecimal, Review> getPriceToBook(StockTicker st, StockPrice last);

	Integer storeValuation(String token, Integer id, BigDecimal expectedGrowth);
    
}
