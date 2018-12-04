package fr.mystocks.mystockserver.service.finance.performance;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;

public interface TechnicalAnalysisService {

	BigDecimal getMovingAverage(StockTicker st, int duration, LocalDate calculationDate);
    
}
