package fr.mystocks.mystockserver.data.finance.stockprice;

import java.util.Comparator;

public class StockPriceComparator implements Comparator<StockPrice> {

    @Override
    public int compare(StockPrice stockPrice1, StockPrice stockPrice2) {
	return stockPrice1.getStockPriceId().getInputDate().compareTo(stockPrice2.getStockPriceId().getInputDate());
    }

}
