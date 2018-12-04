package fr.mystocks.mystockserver.dao.finance.liststockelement;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.liststockelement.ListStockElement;

public interface ListStockElementDao<T> extends Dao<T> {
	
	Integer deleteElements(String login, List<Integer> idsToDelete);

	List<ListStockElement> findByListStockId(Integer idListStock);
    	
}
