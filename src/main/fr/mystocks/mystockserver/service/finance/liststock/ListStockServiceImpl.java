package fr.mystocks.mystockserver.service.finance.liststock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.liststock.ListStockDao;
import fr.mystocks.mystockserver.dao.finance.liststockelement.ListStockElementDao;
import fr.mystocks.mystockserver.dao.finance.stock.StockDao;
import fr.mystocks.mystockserver.data.finance.liststock.ListStock;
import fr.mystocks.mystockserver.data.finance.liststockelement.ListStockElement;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import jersey.repackaged.com.google.common.collect.Lists;

@Service("listStockService")
@Transactional
public class ListStockServiceImpl implements ListStockService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ListStockDao<ListStock> listStockDao;

	@Autowired
	private ListStockElementDao<ListStockElement> listStockElementDao;

	@Autowired
	private StockDao<Stock> stockDao;

	@Override
	public List<ListStock> getListStockByUser(String login, Integer listStockId) {
		try {
			List<ListStock> allLists = listStockDao.getListsByLogin(login);

			// si l'id est fourni on récupère dans cette liste l'id recherché
			if (listStockId != null) {
				for (ListStock ls : allLists) {
					if (ls.getId() == listStockId) {
						return Lists.newArrayList(ls);
					}
				}
			} else {
				return allLists;
			}

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	@Override
	public Integer createListStock(String listName, Integer accountId) {
		try {
			List<ListStock> listsStock = listStockDao.getListsByAccount(accountId);

			listsStock = listsStock.stream().filter(l -> l.getName().equals(listName)).collect(Collectors.toList());

			/* si la liste n'est pas vide il existe déjà une liste avec le même nom */
			if (!listsStock.isEmpty()) {
				throw new FunctionalException(this, "error.finance.list.exist");
			}

			ListStock listStock = new ListStock();
			listStock.setName(listName);
			listStock.setFirstInput(LocalDateTime.now());
			listStock.setAccount(new Account(accountId));
			listStockDao.create(listStock);

			return listStock.getId();

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

	@Override
	public Integer createElementInList(Integer stockId, Integer listStockId) {
		try {

			Stock s = stockDao.findById(stockId);

			ListStock ls = listStockDao.findById(listStockId);

			ListStockElement listStockElement = new ListStockElement();
			listStockElement.setListStock(ls);
			listStockElement.setStock(s);
			listStockElement.setFirstInput(LocalDateTime.now());

			listStockElementDao.create(listStockElement);

			return listStockElement.getId();

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

	@Override
	public Integer deleteList(Integer id) {
		try {

			int numberOfElementDeleted = 1;

			ListStock listStockToDelete = listStockDao.findById(id);

			if (listStockToDelete == null) {
				throw new FunctionalException(this, "error.finance.list.delete.notexist");
			}
			/* on supprime d'abord les éléments de la liste */
//			List<ListStockElement> listsStockELementToDelete = listStockElementDao.findByListStockId(id);
//			numberOfElementDeleted += listsStockELementToDelete.size();
//
//			for (ListStockElement lse : listsStockELementToDelete) {
//				listStockElementDao.delete(lse);
//			}

			listStockDao.delete(listStockToDelete);

			return numberOfElementDeleted;

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

	@Override
	public Integer deleteElements(String login, List<Integer> listToDelete) {
		try {

			return listStockElementDao.deleteElements(login, listToDelete);

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

}
