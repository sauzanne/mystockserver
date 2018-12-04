package fr.mystocks.mystockserver.dao.finance.stock;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.stock.Stock;

public interface StockDao<T> extends Dao<T> {
    
    Stock findByIsin(String isin);
    
    /**
     * @author sauzanne
     * 
     * @param name
     * @return
     */
    List<Stock> findByName(String name);

	
}
