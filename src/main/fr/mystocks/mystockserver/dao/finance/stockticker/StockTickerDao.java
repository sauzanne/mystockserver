package fr.mystocks.mystockserver.dao.finance.stockticker;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

public interface StockTickerDao<T> extends Dao<T> {
	
    /**
     * Recherche par code du ticker et code de la place
     * @param code code du ticker
     * @param codePlace code de la place
     * @param join jointure vers stock and place ?
     * @return un StockTicker
     */
    StockTicker findByCodeAndPlace(String code, String codePlace, boolean join);

	List<StockTicker> findAllEnableStockTicker();


}
