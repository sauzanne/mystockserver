/**
 * 
 */
package fr.mystocks.mystockserver.service.finance.measures;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.measure.MeasureDao;
import fr.mystocks.mystockserver.dao.finance.measurealert.MeasureAlertDao;
import fr.mystocks.mystockserver.dao.finance.measurecalculation.MeasureCalculationDao;
import fr.mystocks.mystockserver.dao.finance.stockticker.StockTickerDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.measurealert.MeasureAlert;
import fr.mystocks.mystockserver.data.finance.measurecalculation.MeasureCalculation;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.service.finance.measures.constant.BinaryOperatorEnum;
import fr.mystocks.mystockserver.service.finance.measures.constant.MeasureEnum;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;

/**
 * @author sauzanne
 *
 */
@Service("measureService")
@Transactional
public class MeasureServiceImpl implements MeasureService {

	@Autowired
	private PropertiesTools propertiesTools;

	@Autowired
	private MeasureAlertDao<MeasureAlert> measureAlertDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StockTickerDao<StockTicker> stockTickerDao;

	@Autowired
	private AccountDao<Account> accountDao;

	@Autowired
	private MeasureDao<Measure> measureDao;

	@Autowired
	private MeasureCalculationDao<MeasureCalculation> measureCalculationDao;

	@Override
	public Integer createMeasureAlert(String login, String codeStockTicker, String codePlace, Integer measureId1,
			Integer measureId2, BigDecimal value, BinaryOperatorEnum binaryOperator) {
		try {
			StockTicker stockTicker = stockTickerDao.findByCodeAndPlace(codeStockTicker, codePlace, false);

			if (stockTicker == null) {
				throw new FunctionalException(this, "error.finance.stockticker.notfound");
			}

			Account account = accountDao.getAccountByLogin(login);
			if (account == null) {
				throw new FunctionalException(this, "error.account.login");
			}

			Measure measure1 = measureDao.findById(measureId1);
			Measure measure2 = null;
			if (measureId2 != null) {
				measure2 = measureDao.findById(measureId1);
			}

			MeasureAlert ma = new MeasureAlert();

			ma.setAccount(account);
			ma.setBinaryOperator(binaryOperator.name());
			ma.setFirstInput(LocalDateTime.now());
			ma.setMeasure(measure1);
			ma.setMeasureCompared(measure2);
			ma.setStockTicker(stockTicker);
			ma.setTriggered(false);
			ma.setValue(value);

			measureAlertDao.create(ma);
			return ma.getId();
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

	@Scheduled(cron = "${cron.measurealert}")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void cronMeasureAlert() {

		logger.error("Measure alert started at : " + LocalDateTime.now());

		List<MeasureAlert> listMeasureAlert = measureAlertDao.getNonTriggeredMeasures();
		try {
			for (MeasureAlert ma : listMeasureAlert) {

				MeasureEnum measureEnum = MeasureEnum.valueOf(ma.getMeasure().getCode());
				if (measureEnum != null) {
					try {

						MeasureCalculation measureCalculation = measureCalculationDao
								.findLastMeasureCalculation(ma.getStockTicker(), ma.getMeasure());
						MeasureCalculation measureCalculationCompared = measureCalculationDao
								.findLastMeasureCalculation(ma.getStockTicker(), ma.getMeasureCompared());

						if (measureCalculation != null && measureCalculationCompared != null) {

							String differentCalculationDate = null;
							/* les 2 dates de calcul des mesures sont différentes */
							if (!measureCalculation.getCalculationDate()
									.isEqual(measureCalculationCompared.getCalculationDate())) {
								propertiesTools.getProperty("measure.mail.alert.calculationdate",
										new String[] { ma.getMeasure().getCode(),
												measureCalculation.getCalculationDate().toString(),
												ma.getMeasureCompared().getCode(),
												measureCalculationCompared.getCalculationDate().toString() });
							}
						} else {

						}

					} catch (FunctionalException e) {
						logger.error("Functional error in calculation of measureAlert id : " + ma.getId());
						ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
					} catch (RuntimeException e) {
						logger.error("Technical error in calculation of measureAlert id : " + ma.getId());
						ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
					}

				} else {
					logger.error("Measure not supported for now : " + ma.getMeasure().getCode());
				}

			}
		} catch (FunctionalException e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		logger.error("Measures calculations ended at " + LocalDateTime.now());
	}

}
