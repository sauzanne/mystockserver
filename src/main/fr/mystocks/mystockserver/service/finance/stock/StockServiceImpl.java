package fr.mystocks.mystockserver.service.finance.stock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import fr.mystocks.mystockserver.dao.finance.stock.StockDao;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.finance.stocktype.StockType;
import fr.mystocks.mystockserver.data.security.User;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@Service("stockService")
@Transactional
public class StockServiceImpl implements StockService {

	@Autowired
	private StockDao<Stock> stockDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Integer storeStock(String isin, String name, Integer stockType, User user, boolean mystocksListed,
			boolean greenrjListed) {
		LocalDateTime now = LocalDateTime.now();
		try {
			Stock stock = stockDao.getStockByIsin(isin);
			if (stock == null) {
				stock = new Stock();
			}
			stock.setGreenrjListed(greenrjListed);
			stock.setMystocksListed(mystocksListed);
			stock.setIsin(isin);
			stock.setName(name);
			stock.setUser(user);
			stock.setAmfNoUpdate(false);
			StockType st = new StockType();
			st.setId(stockType);
			stock.setStockType(st);
			if (stock.getId() == null) {
				stock.setFirstInput(now);
				stockDao.create(stock);
			} else {
				stock.setLastModified(now);
				stockDao.update(stock);
			}
			return stock.getId();
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	@Override
	public List<Stock> findStocks(Integer id, String name, String isin) {
		List<Stock> result = new ArrayList<>();
		try {
			Stock s = null;
			if (id != null) {
				s = stockDao.findById(id);
			} else if (!Strings.isNullOrEmpty(isin)) {
				s = stockDao.getStockByIsin(isin);
			} else if (!Strings.isNullOrEmpty(name)) {
				result.addAll(stockDao.findByName(name));
			}

			if (s != null) {
				result.add(s);
			}

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return result;

	}

}
