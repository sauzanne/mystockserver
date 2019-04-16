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
import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.measurecalculation.MeasureCalculation;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.service.finance.measures.constant.MeasureEnum;
import fr.mystocks.mystockserver.service.finance.performance.TechnicalAnalysisService;
import fr.mystocks.mystockserver.service.finance.performance.ValuationService;
import fr.mystocks.mystockserver.technic.date.DateTools;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;

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
	private MeasureCalculationDao<MeasureCalculation> measureCalculationDao;

	private LocalDate lastMetricsCalculation;

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

					BigDecimal value = null;
					if (measure.getCode().equals(MeasureEnum.MM10.getProperties())) {
						try {
							value = technicalAnalysisService.getMovingAverage(st, 10, calculationDate, 0.1);
							addStat(measure.getCode(), stats);
						} catch (FunctionalException e) {
							logger.error(getFunctionnalLoggingMessage(MeasureEnum.MM10.name(), st));
							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
						} catch (RuntimeException e) {
							logger.error(getErrorLoggingMessage(MeasureEnum.MM10.name(), st));
							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
						}
					} else if (measure.getCode().equals(MeasureEnum.MM150.getProperties())) {

						try {
							value = technicalAnalysisService.getMovingAverage(st, 150, calculationDate,
									acceptableErrorRate);
							addStat(measure.getCode(), stats);

						} catch (FunctionalException e) {
							logger.error(getFunctionnalLoggingMessage(MeasureEnum.MM150.name(), st));
							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
						} catch (RuntimeException e) {
							logger.error(getErrorLoggingMessage(MeasureEnum.MM150.name(), st));
							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
						}
					} else if (measure.getCode().equals(MeasureEnum.MM100.getProperties())) {

						try {
							value = technicalAnalysisService.getMovingAverage(st, 100, calculationDate,
									acceptableErrorRate);
							addStat(measure.getCode(), stats);

						} catch (FunctionalException e) {
							logger.error(getFunctionnalLoggingMessage(MeasureEnum.MM100.name(), st));
							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
						} catch (RuntimeException e) {
							logger.error(getErrorLoggingMessage(MeasureEnum.MM100.name(), st));
							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
						}
					} else if (measure.getCode().equals(MeasureEnum.MM50.getProperties())) {
						try {
							value = technicalAnalysisService.getMovingAverage(st, 50, calculationDate, 0.03);
							addStat(measure.getCode(), stats);

						} catch (FunctionalException e) {
							logger.error(getFunctionnalLoggingMessage(MeasureEnum.MM50.name(), st));
							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
						} catch (RuntimeException e) {
							logger.error(getErrorLoggingMessage(MeasureEnum.MM50.name(), st));
							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
						}
					} else if (measure.getCode().equals(MeasureEnum.MM200.getProperties())) {

						try {
							value = technicalAnalysisService.getMovingAverage(st, 200, calculationDate,
									acceptableErrorRate);
							addStat(measure.getCode(), stats);

						} catch (FunctionalException e) {
							logger.error(getFunctionnalLoggingMessage(MeasureEnum.MM200.name(), st));
							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
						} catch (RuntimeException e) {
							logger.error(getErrorLoggingMessage(MeasureEnum.MM200.name(), st));
							ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
						}
					}
					if (value != null) {
						MeasureCalculation measureCalculation = new MeasureCalculation();

						measureCalculation.setCalculationDate(calculationDate);
						measureCalculation.setFirstInput(LocalDateTime.now());
						measureCalculation.setMeasure(measure);
						measureCalculation.setStockTicker(st);
						measureCalculation.setValue(value);

						measureCalculationDao.create(measureCalculation);
					}

				}
			}
		} catch (FunctionalException e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		} catch (RuntimeException e) {
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
	 * Affchage une erreur fonctionnelle
	 * @param name le nom 
	 * @param st l'action concernée
	 * @return retourne le texte d'une erreur fonctionnelle
	 */
	private String getFunctionnalLoggingMessage(String name, StockTicker st) {
		return "Functional error in calculation of " + name + " for stock ticker " + st.getCode() + "."
				+ st.getPlace().getCode();
	}
	
	/**
	 * Affchage une erreur
	 * @param name le nom 
	 * @param st l'action concernée
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
