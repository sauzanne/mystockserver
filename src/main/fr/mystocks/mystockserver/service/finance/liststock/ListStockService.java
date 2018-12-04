package fr.mystocks.mystockserver.service.finance.liststock;

import java.util.List;

import fr.mystocks.mystockserver.data.finance.liststock.ListStock;

public interface ListStockService {
	
	List<ListStock> getListStockByUser(String userName, Integer listStockId);

	Integer createElementInList(Integer stockId, Integer listStockId);

	Integer deleteElements(String login, List<Integer> listToDelete);

	Integer createListStock(String listName, Integer accountId);

	Integer deleteList(Integer id);

}
