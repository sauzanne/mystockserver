package fr.mystocks.mystockserver.service.finance.performance;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.placeclosing.PlaceClosingDao;
import fr.mystocks.mystockserver.data.finance.placeclosing.PlaceClosing;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.service.finance.stockprice.StockPriceService;
import fr.mystocks.mystockserver.technic.date.DateFinancialTools;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;

@Transactional
@Service("technicalAnalysisService")
public class TechnicalAnalysisServiceImpl implements TechnicalAnalysisService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PlaceClosingDao<PlaceClosing> placeClosingDao;

	@Autowired
	private StockPriceService stockPriceService;

	@Autowired
	private PropertiesTools propertiesTools;

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public BigDecimal getMovingAverage(StockTicker st, int duration, LocalDate calculationDate) {
		try {
			// récupération des jours de fermeture de la place

			List<PlaceClosing> listPlaceClosing = placeClosingDao.findByCodePlace(st.getPlace().getCode());

			int openDaysFind = 0;

			List<LocalDate> listDatePlaceClosing = listPlaceClosing.stream().map(PlaceClosing::getClosing)
					.collect(Collectors.toList());

			List<LocalDate> listCalculationDate = new ArrayList<>();

			LocalDate dateToCheck = calculationDate;
			while (openDaysFind < duration) {

				// on ajoute dans la liste toutes les dates qui sont ouvertes
				if (DateFinancialTools.isOpenDateWithMarketConditions(dateToCheck, listDatePlaceClosing)) {
					listCalculationDate.add(dateToCheck);
					openDaysFind++;
				}
				dateToCheck = dateToCheck.minusDays(1);
			}

			// le dernier élément de la liste constitue la date de début (on remplit par
			// ordre de date décroissant) et le premier élément la dernière date
			List<StockPrice> prices = stockPriceService.getPriceForPeriod(st,
					listCalculationDate.get(listCalculationDate.size() - 1),
					listCalculationDate.stream().findFirst().get());

			if (!prices.isEmpty() && prices.size() == duration) {
				BigDecimal sum = prices.stream().map(StockPrice::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

				return sum.divide(BigDecimal.valueOf(prices.size()), MathContext.DECIMAL128);
			} else if (prices.isEmpty() || prices.size() != duration) {
				throw new FunctionalException(this, "error.finance.stockprice.notall",
						new String[] { new Integer(prices.size()).toString() });
			}
		} catch (FunctionalException e) {
			throw e;
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

}
