package fr.mystocks.mystockserver.service.finance.stock;

import java.util.List;

import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.security.User;

public interface StockService {

    Integer storeStock(String isin, String name, Integer stockType, User user, boolean mystocksListed,
	    boolean greenrjListed);

    List<Stock> findStocks(Integer id, String name, String isin);

}
