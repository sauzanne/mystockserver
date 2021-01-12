package fr.mystocks.mystockserver.service.finance.stockticker;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.place.PlaceDao;
import fr.mystocks.mystockserver.dao.finance.stock.StockDao;
import fr.mystocks.mystockserver.dao.finance.stockticker.StockTickerDao;
import fr.mystocks.mystockserver.data.finance.place.Place;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.data.security.User;
import fr.mystocks.mystockserver.service.finance.stockprice.StockPriceService;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;

@Service("stockTickerService")
@Transactional
public class StockTickerServiceImpl implements StockTickerService {

	@Autowired
	private StockTickerDao<StockTicker> stockTickerDao;

	@Autowired
	private StockPriceService yahooFinanceService;

	@Autowired
	private StockDao<Stock> stockDao;

	@Autowired
	private PlaceDao<Place> placeDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Integer createStockTicker(String isin, String code, Integer idPlace, User user, boolean mainPlace,
			boolean byPassPriceVerification) {
		LocalDateTime now = LocalDateTime.now();
		try {
			Stock stock = stockDao.getStockByIsin(isin);
			if (stock == null) {
				throw new FunctionalException(this, "error.finance.stock.isin.notfound");
			}

			Place place = placeDao.findById(idPlace);

			StockTicker stockTicker = stockTickerDao.findByCodeAndPlace(code, place.getCode(), false);
			if (stockTicker == null) {
				stockTicker = new StockTicker(code, place);
				if (!byPassPriceVerification) {
					if (!yahooFinanceService.checkStocks(stockTicker)) {
						throw new FunctionalException(this, "error.finance.stockticker.price.notfound");
					}
				}
			}
			stockTicker.setCode(code);
			stockTicker.setMainPlace(mainPlace);
			stockTicker.setPlace(place);
			stockTicker.setStock(stock);
			stockTicker.setUser(user);
			if (stockTicker.getId() == null) {
				stockTicker.setFirstInput(now);
				stockTickerDao.create(stockTicker);
			} else {
				stockTicker.setLastModified(now);
				stockTickerDao.update(stockTicker);
			}
			return stockTicker.getId();
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	@Override
	public StockTicker getStockTicker(String code, String placeCode) {
		try {
			return stockTickerDao.findByCodeAndPlace(code, placeCode, true);
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
