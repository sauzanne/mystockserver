package fr.mystocks.mystockserver.dao.finance.liststock;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.liststock.ListStock;

public interface ListStockDao<T> extends Dao<T> {
    
	List<ListStock> getListsByLogin(String login);

	List<ListStock> getListsByAccount(Integer accountId);
	
}
