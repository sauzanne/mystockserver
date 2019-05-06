package fr.mystocks.mystockserver.dao.finance.measurecalculation;

import java.time.LocalDate;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.measurecalculation.MeasureCalculation;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

public interface MeasureCalculationDao<T> extends Dao<T> {

	MeasureCalculation findLastMeasureCalculation(StockTicker st, Measure m);

	MeasureCalculation findMeasureCalculation(StockTicker st, Measure m, LocalDate calculationDate, Review r);
    	
}
