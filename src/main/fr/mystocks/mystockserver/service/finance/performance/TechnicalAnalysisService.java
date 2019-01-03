package fr.mystocks.mystockserver.service.finance.performance;

import java.math.BigDecimal;
import java.time.LocalDate;

import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

public interface TechnicalAnalysisService {

	BigDecimal getMovingAverage(StockTicker st, int duration, LocalDate calculationDate,Double acceptableErrorRate);
    
}
