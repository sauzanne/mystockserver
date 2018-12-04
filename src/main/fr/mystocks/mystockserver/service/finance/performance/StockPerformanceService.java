package fr.mystocks.mystockserver.service.finance.performance;

import java.util.List;

import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;
import fr.mystocks.mystockserver.view.model.finance.stockperformance.StockPerformanceModel;

public interface StockPerformanceService {
	
	
	public List<StockPerformanceModel> comparePerformanceBetween2Periods(String code, String place, Integer startYear, Integer endYear, PeriodEnum period, Integer previousStartYear, Integer previousEndYear, PeriodEnum previousPeriod, String measures);

}
