package fr.mystocks.mystockserver.service.finance.performance;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.placeclosing.PlaceClosingDao;
import fr.mystocks.mystockserver.data.finance.placeclosing.PlaceClosing;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPriceId;
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BigDecimal getMovingAverage(StockTicker st, int duration, LocalDate calculationDate,
			Double acceptableErrorRate) {
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

			Double errorRate = (double) (1 - (new Double(prices.size()) / duration));
			if (!prices.isEmpty() && errorRate <= acceptableErrorRate) {
				BigDecimal sum = prices.stream().map(StockPrice::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

				if (errorRate > 0.0) {
					logger.error("Calcutation of moving average for stock ticker " + st.getCode()
							+ " with an error rate of " + errorRate * 100 + " %");
				}
				return sum.divide(BigDecimal.valueOf(prices.size()), MathContext.DECIMAL128);
			} else if (prices.isEmpty() || errorRate > acceptableErrorRate) {

				// if (!prices.isEmpty()) {
//					Set<String> listStringCalculationDate = 
//						    listCalculationDate.stream()
//						         .map(LocalDate::toString)
//						         .collect(Collectors.toSet());

				List<LocalDate> pricesDate = prices.stream().map(StockPrice::getStockPriceId)
						.map(StockPriceId::getInputDate).collect(Collectors.toList());

//					List<String> missingDate = prices.stream()
//							.filter(p -> !listStringCalculationDate.contains(p.getStockPriceId().getInputDate().toString()))
//							.map(p -> p.getStockPriceId().getInputDate().toString()).collect(Collectors.toList());

				List<String> missingDate = listCalculationDate.stream().filter(p -> !pricesDate.contains(p))
						.map(p -> p.toString()).collect(Collectors.toList());

				logger.error("Calculation fail for moving average of stock : " + st.getCode() + " on place "
						+ st.getPlace().getCode() + " missing dates : " + Strings.join(missingDate, ',')
						+ " with an error rate of " + errorRate * 100 + " %");
				// }
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
