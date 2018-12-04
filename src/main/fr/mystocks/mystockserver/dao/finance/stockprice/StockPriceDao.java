package fr.mystocks.mystockserver.dao.finance.stockprice;

import java.time.LocalDate;
import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

public interface StockPriceDao<T> extends Dao<T> {
	
	List<StockPrice> findByDateRange(StockTicker st, LocalDate startDate, LocalDate endDate);
	
	StockPrice findAtDate(StockTicker st, LocalDate date);


}
