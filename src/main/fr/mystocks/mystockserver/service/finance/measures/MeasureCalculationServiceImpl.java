/**
 * 
 */
package fr.mystocks.mystockserver.service.finance.measures;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
import fr.mystocks.mystockserver.technic.date.DateFinancialTools;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;

/**
 * @author sauzanne
 *
 */
@Service("MeasureCalculationService")
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
		logger.error("Measure calculations started at " + LocalDateTime.now());
		//LocalDate today = LocalDate.now();
		LocalDate today = LocalDate.of(2018, 12, 21);
		if (DateFinancialTools.isOpenDate(today)) {
			try {
				for (StockTicker st : stockTickerDao.findAll()) {

					for (Measure measure : measureDao.findAll()) {

						BigDecimal value = null;
						if (measure.getCode().contains(MeasureEnum.MM10.getProperties())) {
							try {
								value = technicalAnalysisService.getMovingAverage(st, 10, today);
							} catch (FunctionalException e) {
								logger.error("Functional error in calculation of MM10 for stock ticker" + st.getCode());
								ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
							} catch (RuntimeException e) {
								logger.error("Error in calculation of MM10 for stock ticker" + st.getCode());
								ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
							}
						} else if (measure.getCode().contains(MeasureEnum.MM200.getProperties())) {
//							try {
//								value = technicalAnalysisService.getMovingAverage(st, 200, today);
//							} catch (FunctionalException e) {
//								logger.error("Functional error in calculation of MM200 for stock ticker" + st.getCode());
//								ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
//							} catch (RuntimeException e) {
//								logger.error("Error in calculation of MM200 for stock ticker" + st.getCode());
//								ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
//							}
						} else if (measure.getCode().contains(MeasureEnum.MM150.getProperties())) {

							String mm150properties = MeasureEnum.MM150.getProperties();
							try {
								value = technicalAnalysisService.getMovingAverage(st, 150, today);
							} catch (FunctionalException e) {
								logger.error("Functional error in calculation of " + mm150properties
										+ " for stock ticker" + st.getCode());
								ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
							} catch (RuntimeException e) {
								logger.error("Error in calculation of " + mm150properties + " for stock ticker"
										+ st.getCode());
								ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
							}
						}
						if (value != null) {
							MeasureCalculation measureCalculation = new MeasureCalculation();

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
			lastMetricsCalculation = today;
			logger.error("Measures calculations ended at " + LocalDateTime.now());

		} else {
			logger.error("No execution today : markets are not open today !");
		}
	}

}
