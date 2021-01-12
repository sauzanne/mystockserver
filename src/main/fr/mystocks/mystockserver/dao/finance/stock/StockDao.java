package fr.mystocks.mystockserver.dao.finance.stock;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.stock.Stock;

public interface StockDao<T> extends Dao<T> {
    
    /**
     * Find a stock by isin
     * @param isin isin code (usage of like allowed)
     * @return 
     */
    Stock getStockByIsin(String isin);
    
    /**
     * @author sauzanne
     * 
     * @param name
     * @return
     */
    List<Stock> findByName(String name);

	List<Stock> findByIsin(String isin);
    


	
}
