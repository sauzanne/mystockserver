/**
 * 
 */
package fr.mystocks.mystockserver.service.finance.measures;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import fr.mystocks.mystockserver.dao.finance.measure.MeasureDao;
import fr.mystocks.mystockserver.dao.finance.measurealert.MeasureAlertDao;
import fr.mystocks.mystockserver.dao.finance.measurecalculation.MeasureCalculationDao;
import fr.mystocks.mystockserver.dao.finance.stockticker.StockTickerDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.measurealert.MeasureAlert;
import fr.mystocks.mystockserver.data.finance.measurecalculation.MeasureCalculation;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.service.finance.measures.constant.BinaryOperatorEnum;
import fr.mystocks.mystockserver.service.finance.measures.constant.MeasureEnum;
import fr.mystocks.mystockserver.service.finance.stockprice.StockPriceService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.mail.MailTools;
import fr.mystocks.mystockserver.technic.number.NumberFinancialTools;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;
import fr.mystocks.mystockserver.view.model.finance.measure.MeasureAlertModel;

/**
 * @author sauzanne
 *
 */
@Service("measureService")
@Transactional
public class MeasureServiceImpl implements MeasureService {

	@Inject
	private SpringConfiguration context;

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

	@Autowired
	private MailTools mailTools;

	@Autowired
	private StockPriceService stockPriceService;

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
				measure2 = measureDao.findById(measureId2);
			}

			List<MeasureAlert> allreadyDefinedAlerts = measureAlertDao.findMeasureAlert(account.getId(),
					stockTicker.getId(), measureId1, measure2 != null ? measure2.getId() : null, binaryOperator.name(),
					false);

			if (allreadyDefinedAlerts != null && !allreadyDefinedAlerts.isEmpty()) {
				throw new FunctionalException(this, "error.finance.measurealert.exist");

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

				MeasureEnum measureEnum = MeasureEnum.getMeasureEnumByProperties(ma.getMeasure().getCode());
				if (measureEnum != null) {
					try {
						MeasureCalculation measureCalculation = getMeasureCalculationFromMeasureAlert(ma, measureEnum);

						MeasureCalculation measureCalculationCompared = null;

						if (ma.getMeasureCompared() != null) {
							measureCalculationCompared = getMeasureCalculationComparedFromMeasureAlert(ma);
						}

						/* vérification du déclenchement de l'alerte */
						if (measureCalculation != null && measureCalculationCompared != null) {
							triggerAlert(ma, measureEnum, measureCalculation, measureCalculationCompared);

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
		logger.error("Measures alert ended at " + LocalDateTime.now());
	}

	private MeasureCalculation getMeasureCalculationComparedFromMeasureAlert(MeasureAlert ma) {
		MeasureCalculation measureCalculationCompared = measureCalculationDao
				.findLastMeasureCalculation(ma.getStockTicker(), ma.getMeasureCompared());

		if (measureCalculationCompared == null && ma.getMeasureCompared() != null) {
			MeasureEnum measureEnumCompared = MeasureEnum.getMeasureEnumByProperties(ma.getMeasureCompared().getCode());

			if (measureEnumCompared == MeasureEnum.LASTPRICE) {
				StockPrice last = stockPriceService.getLast(ma.getStockTicker());
				if (last != null) {
					measureCalculationCompared = new MeasureCalculation();
					measureCalculationCompared.setCalculationDate(LocalDate.now());
					measureCalculationCompared.setValue(last.getPrice());
				}
			}

		}
		return measureCalculationCompared;
	}

	private MeasureCalculation getMeasureCalculationFromMeasureAlert(MeasureAlert ma, MeasureEnum measureEnum) {
		MeasureCalculation measureCalculation = null;
		if (measureEnum != MeasureEnum.LASTPRICE) {
			measureCalculation = measureCalculationDao.findLastMeasureCalculation(ma.getStockTicker(), ma.getMeasure());
		} else {
			StockPrice last = stockPriceService.getLast(ma.getStockTicker());
			if (last != null) {
				measureCalculation = new MeasureCalculation();
				measureCalculation.setCalculationDate(LocalDate.now());
				measureCalculation.setValue(last.getPrice());
			}

		}
		return measureCalculation;
	}

	/**
	 * Vérifie les conditions qui permettent de déclencher l'alerte
	 * 
	 * @param ma                         l'alerte mesurée
	 * @param measureEnum                le type de mesure
	 * @param measureCalculation         le calcul de la mesure
	 * @param measureCalculationCompared le calcul comparé
	 */
	private void triggerAlert(MeasureAlert ma, MeasureEnum measureEnum, MeasureCalculation measureCalculation,
			MeasureCalculation measureCalculationCompared) {
		BinaryOperatorEnum binaryOperator = BinaryOperatorEnum.valueOf(ma.getBinaryOperator());

		if ((binaryOperator == BinaryOperatorEnum.GE
				&& measureCalculation.getValue().compareTo(measureCalculationCompared.getValue()) >= 0)
				|| (binaryOperator == BinaryOperatorEnum.LE
						&& measureCalculation.getValue().compareTo(measureCalculationCompared.getValue()) <= 0))

		{
			String differentCalculationDate = null;
			/* les 2 dates de calcul des mesures sont différentes */
			if (!measureCalculation.getCalculationDate().isEqual(measureCalculationCompared.getCalculationDate())) {
				differentCalculationDate = propertiesTools.getProperty("measure.mail.alert.calculationdate",
						new String[] { ma.getMeasure().getCode(), measureCalculation.getCalculationDate().toString(),
								ma.getMeasureCompared().getCode(),
								measureCalculationCompared.getCalculationDate().toString() });
			}
			MeasureEnum measureEnumCompared = MeasureEnum.getMeasureEnumByProperties(ma.getMeasureCompared().getCode());

			// ma.getStockTicker().
			String subject = propertiesTools.getProperty("measure.mail.alert.subject", new String[] {
					ma.getStockTicker().getStock().getName() + "(" + ma.getStockTicker().getCode().toUpperCase() + "."
							+ ma.getStockTicker().getPlace().getCode().toUpperCase() + ")",
					measureEnum.name(), propertiesTools.getProperty(binaryOperator.getProperties()),
					measureEnumCompared.name() });

			String body = propertiesTools.getProperty("measure.mail.alert.body",
					new String[] { ma.getAccount().getFirstName(), ma.getStockTicker().getStock().getName() + "("
							+ ma.getStockTicker().getCode() + "." + ma.getStockTicker().getPlace().getCode() + ")",
							measureEnum.name(),
							NumberFinancialTools.defaultNumberFormat(measureCalculation.getValue(),
									context.getLocale()),
							propertiesTools.getProperty(binaryOperator.getProperties()), measureEnumCompared.name(),
							NumberFinancialTools.defaultNumberFormat(measureCalculationCompared.getValue(),
									context.getLocale()) });

			try {
				mailTools.sendMessage(ma.getAccount().getMail(), subject,
						body + Strings.nullToEmpty(differentCalculationDate));
			} catch (RuntimeException e) {
				logger.error("Impossible to send email for measureAlert id : " + ma.getId() + " to "
						+ ma.getAccount().getMail());
				ExceptionTools.processException(this, logger, e);
			}

			/* on met à jour l'alerte */
			ma.setTriggered(true);
			ma.setLastModified(LocalDateTime.now());
			measureAlertDao.update(ma);

		}
	}

	@Override
	public List<MeasureAlertModel> getAllMeasure(String login, boolean triggered) {
		List<MeasureAlertModel> listMeasureAlertModel = new ArrayList<>();
		try {

			Account account = accountDao.getAccountByLogin(login);
			if (account == null) {
				throw new FunctionalException(this, "error.account.login");
			}

			List<MeasureAlert> listMeasureAlert = measureAlertDao.findMeasureAlert(account.getId(), null, null, null,
					null, triggered);

			for (MeasureAlert ma : listMeasureAlert) {

				MeasureAlertModel mam = new MeasureAlertModel();

				MeasureEnum measureEnum = MeasureEnum.getMeasureEnumByProperties(ma.getMeasure().getCode());
				if (measureEnum != null) {

					mam.setMeasure(measureEnum.name());
					mam.setComparator(ma.getBinaryOperator());
					mam.setMeasureCompared(ma.getMeasureCompared() != null
							? MeasureEnum.getMeasureEnumByProperties(ma.getMeasureCompared().getCode()).name()
							: null);
					mam.setStockName(ma.getStockTicker().getStock().getName());
					mam.setStockTicker(ma.getStockTicker().getCode());

					MeasureCalculation measureCalculation = getMeasureCalculationFromMeasureAlert(ma, measureEnum);
					if (measureCalculation != null) {
						mam.setMeasureCalculation(measureCalculation.getValue().doubleValue());
					}
					MeasureCalculation measureCalculationCompared = null;
					if (ma.getMeasureCompared() != null) {
						measureCalculationCompared = getMeasureCalculationComparedFromMeasureAlert(ma);
						if (measureCalculationCompared != null) {
							mam.setMeasureCalculationCompared(measureCalculationCompared.getValue().doubleValue());
						}

					}
					mam.setTriggered(ma.isTriggered());

					listMeasureAlertModel.add(mam);

				}
			}

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return listMeasureAlertModel;
	}

}
