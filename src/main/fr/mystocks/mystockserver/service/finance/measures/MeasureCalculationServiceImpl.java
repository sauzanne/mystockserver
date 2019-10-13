/**
 * 
 */
package fr.mystocks.mystockserver.service.finance.measures;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.measure.MeasureDao;
import fr.mystocks.mystockserver.dao.finance.measurecalculation.MeasureCalculationDao;
import fr.mystocks.mystockserver.dao.finance.stockticker.StockTickerDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.measurecalculation.MeasureCalculation;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.service.finance.measures.constant.MeasureEnum;
import fr.mystocks.mystockserver.service.finance.performance.TechnicalAnalysisService;
import fr.mystocks.mystockserver.service.finance.performance.ValuationService;
import fr.mystocks.mystockserver.service.finance.stockprice.StockPriceService;
import fr.mystocks.mystockserver.technic.date.DateTools;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;
import fr.mystocks.mystockserver.technic.util.DoubleReturnValue;

/**
 * @author sauzanne
 *
 */
@Service("measureCalculationService")
@Transactional
@PropertySources({ @PropertySource(value = { "classpath:/application.properties" }) })
public class MeasureCalculationServiceImpl implements MeasureCalculationService {

	@Autowired
	private PropertiesTools propertiesTools;

	@Autowired
	private TechnicalAnalysisService technicalAnalysisService;

	@Autowired
	private StockTickerDao<StockTicker> stockTickerDao;

	@Autowired
	private MeasureDao<Measure> measureDao;

	@Autowired
	private AccountDao<Account> accountDao;

	@Autowired
	private MeasureCalculationDao<MeasureCalculation> measureCalculationDao;

	private LocalDate lastMetricsCalculation;

	@Autowired
	private StockPriceService stockPriceService;

	@Autowired
	private ValuationService valuationService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Scheduled(cron = "${cron.measurecalculation}")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void cronMeasureCalculation() {
		double acceptableErrorRate = 0.02;
		logger.error("Measure calculations started at " + LocalDateTime.now() + " acceptable error rate fixed at "
				+ acceptableErrorRate * 100 + " %");
		LocalDate calculationDate = DateTools.getPreviousOpenDate(LocalDate.now());
		// LocalDate calculationDate = LocalDate.of(2018, 12, 31);
		Map<String, Integer> stats = new HashMap<>();
		List<StockTicker> listStockerTicker = stockTickerDao.findAllEnableStockTicker();
		try {
			for (StockTicker st : listStockerTicker) {

				for (Measure measure : measureDao.getAvailableMeasures()) {

					DoubleReturnValue<BigDecimal, Review> value = null;
//					StockPrice last = null;
//					try {
//						last = stockPriceService.getLast(st);
//					} catch (RuntimeException e) {
//						ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
//					}
					if (measure.getCode().equals(MeasureEnum.MM10.getProperties())) {
						value = getMovingAverage(calculationDate, stats, st, measure, 0.1);
					} else if (measure.getCode().equals(MeasureEnum.MM150.getProperties())) {
						value = getMovingAverage(calculationDate, stats, st, measure, acceptableErrorRate);
					} else if (measure.getCode().equals(MeasureEnum.MM100.getProperties())) {
						value = getMovingAverage(calculationDate, stats, st, measure, acceptableErrorRate);
					} else if (measure.getCode().equals(MeasureEnum.MM50.getProperties())) {
						value = getMovingAverage(calculationDate, stats, st, measure, 0.03);
					} else if (measure.getCode().equals(MeasureEnum.MM200.getProperties())) {
						value = getMovingAverage(calculationDate, stats, st, measure, acceptableErrorRate);
					} else if (measure.getCode().equals(MeasureEnum.VALUATION_PE.getProperties())) {
						value = valuationService.getPriceEarnings(st, null);
						if (value != null) {
							addStat(measure.getCode(), stats);
						}
					} else if (measure.getCode().equals(MeasureEnum.VALUATION_PB.getProperties())) {
						value = valuationService.getPriceToBook(st, null);
						if (value != null) {
							addStat(measure.getCode(), stats);
						}
					}
					if (value != null) {
						MeasureCalculation measureCalculation = measureCalculationDao.findMeasureCalculation(st,
								measure, calculationDate, value.getValue2());

						if (measureCalculation == null) {
							measureCalculation = new MeasureCalculation();
							measureCalculation.setFirstInput(LocalDateTime.now());
						}

						measureCalculation.setCalculationDate(calculationDate);
						measureCalculation.setMeasure(measure);
						measureCalculation.setStockTicker(st);
						measureCalculation.setValue(value.getValue1());
						measureCalculation.setReview(value.getValue2());

						if (measureCalculation.getId() == null) {
							measureCalculationDao.create(measureCalculation);
						} else {
							measureCalculation.setLastModified(LocalDateTime.now());
							measureCalculationDao.update(measureCalculation);
						}
//						} catch (Exception e) {
//							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
//						}
					}

				}
			}
		} catch (FunctionalException e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		} catch (Exception e) {
			ExceptionTools.processException(this, logger, e);
		}
		lastMetricsCalculation = calculationDate;
		for (Entry<String, Integer> entry : stats.entrySet()) {
			logger.error("Calculations stats for " + entry.getKey() + " : "
					+ (new Double(entry.getValue()) / new Double(listStockerTicker.size()) * 100) + " %");
		}

		logger.error("Measures calculations ended at " + LocalDateTime.now());
	}

	/**
	 * @param calculationDate date du calcul
	 * @param stats           stats de calcul
	 * @param st              stockTicker
	 * @param measure         mesure
	 * @param errorRate       taux d'erreur acceptable
	 * @return le résultat du calcul
	 */
	private DoubleReturnValue<BigDecimal, Review> getMovingAverage(LocalDate calculationDate,
			Map<String, Integer> stats, StockTicker st, Measure measure, Double errorRate) {
		DoubleReturnValue<BigDecimal, Review> value = null;
		MeasureEnum measureEnum = MeasureEnum.getMeasureEnumByProperties(measure.getCode());
		try {

			value = new DoubleReturnValue<>(technicalAnalysisService.getMovingAverage(st, measureEnum.getDuration(),
					calculationDate, errorRate), null);
			addStat(measureEnum.getProperties(), stats);
		} catch (FunctionalException e) {
			logger.error(getFunctionnalLoggingMessage(measureEnum.name(), st));
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		} catch (RuntimeException e) {
			logger.error(getErrorLoggingMessage(measureEnum.name(), st));
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		}
		return value;
	}

	/**
	 * Affchage une erreur fonctionnelle
	 * 
	 * @param name le nom
	 * @param st   l'action concernée
	 * @return retourne le texte d'une erreur fonctionnelle
	 */
	private String getFunctionnalLoggingMessage(String name, StockTicker st) {
		return "Functional error in calculation of " + name + " for stock ticker " + st.getCode() + "."
				+ st.getPlace().getCode();
	}

	/**
	 * Affchage une erreur
	 * 
	 * @param name le nom
	 * @param st   l'action concernée
	 * @return retourne le texte d'une erreur fonctionnelle
	 */
	private String getErrorLoggingMessage(String name, StockTicker st) {
		return "Runtime error in calculation of " + name + " for stock ticker " + st.getCode() + "."
				+ st.getPlace().getCode();
	}

	private void addStat(String code, Map<String, Integer> stats) {
		stats.put(code, Optional.ofNullable(stats.get(code)).orElse(0) + 1);

	}

}
