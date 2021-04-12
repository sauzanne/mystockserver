/**
 * 
 */
package fr.mystocks.mystockserver.service.finance.measures;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;
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
			Integer measureId2, BigDecimal value, BinaryOperatorEnum binaryOperator, String comment) {

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
			if (!measure1.isAvailable()) {
				throw new FunctionalException(this, "error.finance.measure.notavailable",
						new String[] { measure1.getCode() });
			}
			Measure measure2 = null;
			if (measureId2 != null) {
				measure2 = measureDao.findById(measureId2);
				if (!measure2.isAvailable()) {
					throw new FunctionalException(this, "error.finance.measure.notavailable",
							new String[] { measure1.getCode() });
				}
			}

			List<MeasureAlert> allreadyDefinedAlerts = measureAlertDao.findMeasureAlert(account.getId(),
					stockTicker.getId(), measureId1, measure2 != null ? measure2.getId() : null, value,
					binaryOperator.name(), false);

			if (allreadyDefinedAlerts != null && !allreadyDefinedAlerts.isEmpty()) {
				throw new FunctionalException(this, "error.finance.alert.exist");

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
			ma.setComment(Strings.emptyToNull(Strings.nullToEmpty(comment).trim()));

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
						if (measureCalculation != null
								&& (measureCalculationCompared != null || ma.getValue() != null)) {
							triggerAlert(ma, measureEnum, measureCalculation, measureCalculationCompared,
									ma.getValue());

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
	 * @param measureCalculationCompared le calcul comparé (nullable)
	 * @param value                      la valeur comparée (nullable)
	 */
	private void triggerAlert(MeasureAlert ma, MeasureEnum measureEnum, MeasureCalculation measureCalculation,
			MeasureCalculation measureCalculationCompared, BigDecimal value) {
		BinaryOperatorEnum binaryOperator = BinaryOperatorEnum.valueOf(ma.getBinaryOperator());
		BigDecimal valueToCompare = measureCalculationCompared != null ? measureCalculationCompared.getValue() : value;

		if ((binaryOperator == BinaryOperatorEnum.GE && measureCalculation.getValue().compareTo(valueToCompare) >= 0)
				|| (binaryOperator == BinaryOperatorEnum.LE
						&& measureCalculation.getValue().compareTo(valueToCompare) <= 0))

		{
			String differentCalculationDate = null;
			String basedOnReview = null;
			/* les 2 dates de calcul des mesures sont différentes */
			if (measureCalculationCompared != null && !measureCalculation.getCalculationDate()
					.isEqual(measureCalculationCompared.getCalculationDate())) {
				differentCalculationDate = propertiesTools.getProperty("measure.mail.alert.calculationdate",
						new String[] { ma.getMeasure().getCode(), measureCalculation.getCalculationDate().toString(),
								ma.getMeasureCompared().getCode(),
								measureCalculationCompared.getCalculationDate().toString() });
			}
			if (measureCalculation.getReview() != null) {
				boolean startYearsEqualsToEndYear = (Optional.ofNullable(measureCalculation.getReview().getEndYear())
						.orElse(0)).equals(measureCalculation.getReview().getStartYear());
				basedOnReview = propertiesTools.getProperty("measure.mail.alert.review",
						new String[] { ma.getMeasure().getCode(), propertiesTools.getProperty(PeriodEnum
								.valueOf(measureCalculation.getReview().getPeriod().toUpperCase()).getKeyDescription()),
								measureCalculation.getReview().getStartYear().toString() + (!startYearsEqualsToEndYear
										? ("-" + measureCalculation.getReview().getEndYear().toString())
										: "") });

			}
			MeasureEnum measureEnumCompared = MeasureEnum.getMeasureEnumByProperties(
					Optional.ofNullable(ma.getMeasureCompared()).orElse(new Measure()).getCode());

			// ma.getStockTicker().
			String subject = propertiesTools.getProperty("measure.mail.alert.subject",
					new String[] {
							ma.getStockTicker().getStock().getName() + "(" + ma.getStockTicker().getCode().toUpperCase()
									+ "." + ma.getStockTicker().getPlace().getCode().toUpperCase() + ")",
							measureEnum.name(), propertiesTools.getProperty(binaryOperator.getProperties()),
							measureEnumCompared != null ? measureEnumCompared.name()
									: NumberFinancialTools.defaultNumberFormat(valueToCompare, context.getLocale()) });
			String body = null;

			String comment = null;

			if (ma.getComment() != null) {
				comment = propertiesTools.getProperty("measure.mail.alert.comment", new String[] { ma.getComment() });
			}
			if (measureEnumCompared != null) {
				body = propertiesTools.getProperty("measure.mail.alert.body",
						new String[] { ma.getAccount().getFirstName(), ma.getStockTicker().getStock().getName() + "("
								+ ma.getStockTicker().getCode() + "." + ma.getStockTicker().getPlace().getCode() + ")",
								measureEnum.name(),
								NumberFinancialTools.defaultNumberFormat(measureCalculation.getValue(),
										context.getLocale()),
								propertiesTools.getProperty(binaryOperator.getProperties()), measureEnumCompared.name(),
								NumberFinancialTools.defaultNumberFormat(measureCalculationCompared.getValue(),
										context.getLocale()),
								Strings.nullToEmpty(comment) });
			} else {
				body = propertiesTools.getProperty("measure.mail.alert.body",
						new String[] { ma.getAccount().getFirstName(), ma.getStockTicker().getStock().getName() + "("
								+ ma.getStockTicker().getCode() + "." + ma.getStockTicker().getPlace().getCode() + ")",
								measureEnum.name(),
								NumberFinancialTools.defaultNumberFormat(measureCalculation.getValue(),
										context.getLocale()),
								propertiesTools.getProperty(binaryOperator.getProperties()),
								propertiesTools.getProperty("common.value"),
								NumberFinancialTools.defaultNumberFormat(valueToCompare, context.getLocale()),
								Strings.nullToEmpty(comment) });

			}

			try {
				mailTools.sendMessage(ma.getAccount().getMail(), subject,
						body + Strings.nullToEmpty(differentCalculationDate) + Strings.nullToEmpty(basedOnReview));
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
					null, null, triggered);

			for (MeasureAlert ma : listMeasureAlert) {

				MeasureAlertModel mam = new MeasureAlertModel();

				MeasureEnum measureEnum = MeasureEnum.getMeasureEnumByProperties(ma.getMeasure().getCode());
				if (measureEnum != null) {

					mam.setMeasure(measureEnum.name());
					mam.setComparator(ma.getBinaryOperator());
					mam.setMeasureCompared(ma.getMeasureCompared() != null
							? MeasureEnum.getMeasureEnumByProperties(ma.getMeasureCompared().getCode()).name()
							: propertiesTools.getProperty("common.value").toUpperCase());
					mam.setStockName(ma.getStockTicker().getStock().getName());
					mam.setStockTicker(ma.getStockTicker().getCode());

					mam.setComment(Strings.nullToEmpty(ma.getComment()));

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
					} else {
						mam.setMeasureCalculationCompared(ma.getValue().doubleValue());

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
