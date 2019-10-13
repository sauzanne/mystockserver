package fr.mystocks.mystockserver.service.finance.stockprice;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.placeclosing.PlaceClosingDao;
import fr.mystocks.mystockserver.dao.finance.stockprice.StockPriceDao;
import fr.mystocks.mystockserver.dao.finance.stockticker.StockTickerDao;
import fr.mystocks.mystockserver.data.finance.placeclosing.PlaceClosing;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPriceComparator;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.technic.date.DateFinancialTools;
import fr.mystocks.mystockserver.technic.date.DateTools;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;

@Service("stockPriceService")
@Transactional
public class StockPriceServiceImpl implements StockPriceService {

	@Autowired
	private StockPriceDao<StockPrice> stockPriceDao;

	@Autowired
	private StockPriceService yahooFinanceService;

	@Autowired
	private StockTickerDao<StockTicker> stockTickerDao;

	@Autowired
	private PlaceClosingDao<PlaceClosing> placeClosingDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	//@Cacheable(value = "financeCacheShortTime", key = "#root.targetClass+#root.methodName+#st.getId()")
	public StockPrice getLast(StockTicker st) {
		try {

			LocalDate lastOpenDate = DateTools.getLastOpenDate(LocalDate.now());
			StockPrice stockPriceDB = stockPriceDao.findAtDate(st, lastOpenDate);
			boolean today = lastOpenDate.equals(LocalDate.now());

			StockPrice stockPrice = null;
			/*
			 * on fait pas une requête que si on considère que la journée n'est pas cloturée
			 * ou si on a aucun cours d'enregistré
			 */
			if (stockPriceDB == null || (today && !stockPriceDB.getClose())) {
				stockPrice = yahooFinanceService.getLast(st);
			}

			if (stockPrice != null) {
				StockTicker stockTicker = stockTickerDao.findByCodeAndPlace(st.getCode(), st.getPlace().getCode(),
						false);

				if (stockTicker == null) {
					throw new FunctionalException(this, "error.finance.stockticker.notfound");
				}
				stockPrice.getStockPriceId().setStockTicker(stockTicker);

				if (stockPrice.getStockPriceId().getInputDate().equals(lastOpenDate)) {
					/* cas 1 : la donnée existe déjà en base */
					if (stockPriceDB != null) {
						stockPriceDB.setClose(today ? false : true);
						stockPriceDB.setPrice(stockPrice.getPrice());
						stockPriceDao.update(stockPriceDB);
					} else /* cas 2 : la donnée n'existe pas */
					{
						stockPriceDao.create(stockPrice);
						return stockPrice;
					}
				} else if (!stockPrice.getStockPriceId().getInputDate().equals(lastOpenDate)) {
					/*
					 * cas 3 : si le prix retourné n'est pas à la date attendue, on vérifie si le
					 * prix n'est pas déjà présent en base
					 */
					stockPriceDB = stockPriceDao.findAtDate(stockPrice.getStockPriceId().getStockTicker(),
							stockPrice.getStockPriceId().getInputDate());

					if (stockPriceDB == null) {
						stockPrice.setClose(true);
						stockPriceDao.create(stockPrice);
						return stockPrice;
					} else if (!stockPriceDB.getClose()) { /*
															 * cas 4 : on met à jour le prix en base de donnée seulement
															 * si ce n'est pas déjà un prix de cloture
															 */
						stockPriceDB.setClose(true);
						stockPriceDB.setPrice(stockPrice.getPrice());
						stockPriceDao.update(stockPriceDB);
					}
				}
			}
			/* dans tous les cas on retourne la donnée éventuelle en base */
			return stockPriceDB;
		} catch (Exception e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	@Override
	//@Cacheable(value = "financeCacheShortTime", key = "#root.methodName+#st.getId()+#start.toString()+#end.toString()")
	public List<StockPrice> getPriceForPeriod(StockTicker st, LocalDate start, LocalDate end, Boolean... repeat) {
		try {
			List<StockPrice> stockPrices = stockPriceDao.findByDateRange(st, start, end);

			List<PlaceClosing> listPlaceClosing = placeClosingDao.findByCodePlace(st.getPlace().getCode());
			List<LocalDate> listDatePlaceClosing = listPlaceClosing.stream().map(PlaceClosing::getClosing)
					.collect(Collectors.toList());

			if (stockPrices != null) {

				LocalDate dateToLoop = start;

				LocalDate startDateToFind = null;
				/*
				 * on recherche la première date sur laquelle on n'a pas de résultats
				 */
				while (dateToLoop.isBefore(end) || dateToLoop.isEqual(end)) {
					if (DateFinancialTools.isOpenDateWithMarketConditions(dateToLoop, listDatePlaceClosing)) {
						StockPrice stockPrice = DateFinancialTools.getStockPriceFromDate(stockPrices, dateToLoop);
						if (stockPrice == null || !stockPrice.getClose()) {
							/*
							 * on a trouvé une date où il n'y a pas de prix ou ce n'est pas le prix à la
							 * clotûre des marchés
							 */
							startDateToFind = dateToLoop;
							break;
						}
					}
					/* on avance d'un jour */
					dateToLoop = dateToLoop.plusDays(1);
				}

				if (startDateToFind != null) {
					/* on recherche les cours avec une API */
					List<StockPrice> stockPricesFromApi = yahooFinanceService.getPriceForPeriod(st, startDateToFind,
							end);

					StockTicker stockTicker = stockTickerDao.findByCodeAndPlace(st.getCode(), st.getPlace().getCode(),
							false);

					if (stockTicker == null) {
						throw new FunctionalException(this, "error.finance.stockticker.notfound");
					}

					/*
					 * on procède à la fusion des 2 listes, et à leur enregistrement en base
					 */
					if (stockPricesFromApi != null) {
						LocalDate today = LocalDate.now();

						for (StockPrice sp : stockPricesFromApi) {
							StockPrice stockPrice = storePriceinDB(end, stockPrices, stockTicker, today, sp);
							if (stockPrice != null) {
								stockPrices.add(stockPrice);
							}
						}
					}
					/* on retrie l'ensemble fusionné par date */
					Collections.sort(stockPrices, new StockPriceComparator());
				}
			}
			return stockPrices;
		} catch (Exception e) {
			/* keep Exception to catch IOException */
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	/**
	 * Enregistre le prix en base
	 * 
	 * @param end               date de butée
	 * @param stockPricesFromDb prix obtenus depuis la base de donnée
	 * @param stockTicker       stockTicker
	 * @param today             la date du jour
	 * @param stockPriceFromApi le prix obtenu depuis l'API
	 * @return stockPrice le stock price crée en base
	 */
	private StockPrice storePriceinDB(LocalDate end, List<StockPrice> stockPricesFromDb, StockTicker stockTicker,
			LocalDate today, StockPrice stockPriceFromApi) {
		/*
		 * en cas de problème de création en base on ne souhaite pas interrompre le
		 * processus
		 */
		try {
			/*
			 * on crée à plusieurs conditions : il y a un prix et la date ne se situe pas
			 * après la date de fin reçue en paramètre
			 */
			if (stockPriceFromApi.getPrice() != null
					&& !stockPriceFromApi.getStockPriceId().getInputDate().isAfter(end)) {
				/* on cherche si ce prix n'est pas déjà présent en base */
				StockPrice stockPriceDb = DateFinancialTools.getStockPriceFromDate(stockPricesFromDb,
						stockPriceFromApi.getStockPriceId().getInputDate());

				if (stockPriceFromApi.getStockPriceId().getInputDate().isBefore(today)) {
					stockPriceFromApi.setClose(Boolean.TRUE);
				}

				/* il faut créer un élément en base */
				if (stockPriceDb == null) {
					stockPriceFromApi.getStockPriceId().setStockTicker(stockTicker);
					stockPriceDao.create(stockPriceFromApi);
					return stockPriceFromApi;
				} else {
					stockPriceDb.setPrice(stockPriceFromApi.getPrice());
					stockPriceDb.setClose(stockPriceFromApi.getClose());
					stockPriceDao.update(stockPriceDb);
				}
			}
		} catch (RuntimeException e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		}
		return null;
	}

	@Override
	public boolean checkStocks(StockTicker st) {
		try {
			// on vérifie une première fois
			return yahooFinanceService.checkStocks(st) ? true
					: stockTickerDao.findByCodeAndPlace(st.getCode(), st.getPlace().getCode(), false) != null;
			// return ;

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return false;
	}

	@Override
	//@Cacheable(value = "financeCacheShortTime", key = "#root.methodName+#st.getId()+#start.toString()+#end.toString()")
	public BigDecimal getAveragePrice(StockTicker st, LocalDate start, LocalDate end) {
		try {
			List<StockPrice> prices = getPriceForPeriod(st, start, end);

			if (!prices.isEmpty()) {
				BigDecimal sum = prices.stream().map(StockPrice::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

				return sum.divide(BigDecimal.valueOf(prices.size()), MathContext.DECIMAL128);
			}

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
