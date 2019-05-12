package fr.mystocks.mystockserver.service.finance.performance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import fr.mystocks.mystockserver.dao.finance.measure.MeasureDao;
import fr.mystocks.mystockserver.dao.finance.review.ReviewDao;
import fr.mystocks.mystockserver.dao.finance.stockticker.StockTickerDao;
import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;
import fr.mystocks.mystockserver.service.finance.stockprice.StockPriceService;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;
import fr.mystocks.mystockserver.technic.util.DoubleReturnValue;
import fr.mystocks.mystockserver.view.model.finance.stockperformance.StockPerformanceModel;

@Service("stockPerformanceService")
@Transactional
public class StockPerformanceServiceImpl implements StockPerformanceService {

	@Autowired
	private StockTickerDao<StockTicker> stockTickerDao;

	@Autowired
	private MeasureDao<Measure> measureDao;

	@Autowired
	private ReviewDao<Review> reviewDao;

	private List<Measure> listMeasure;

	@Autowired
	private ValuationService valuationService;

	@Autowired
	private StockPriceService stockPriceService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String KEY_PERFORMANCE_REQUIRED = "error.finance.performance.required";

	@Autowired
	private PropertiesTools propertiesTools;

	@Override
	public List<StockPerformanceModel> comparePerformanceBetween2Periods(String code, String place, Integer startYear,
			Integer endYear, PeriodEnum period, Integer previousStartYear, Integer previousEndYear,
			PeriodEnum previousPeriod, String measures) {

		StockTicker st = stockTickerDao.findByCodeAndPlace(code, place, true);

		if (st == null) {
			throw new FunctionalException(this, "error.finance.stockticker.notfound");
		}

		DoubleReturnValue<Review, Review> reviews = getReviews(startYear, endYear, period, previousStartYear,
				previousEndYear, previousPeriod, st);
		try {

			List<Measure> listMeasureToExecute = new ArrayList<>();

			if (Strings.isNullOrEmpty(measures)) {
				listMeasureToExecute = getListMeasure();
			} else {
				listMeasureToExecute = filterMeasure(Arrays.asList(measures.split(",")));

			}

			StockPrice last = stockPriceService.getLast(st);
			List<StockPerformance> listStockPerformanceReview = getPerformanceForReview(reviews.getValue1(),
					listMeasureToExecute, last, st);

			if (reviews.getValue2() != null) {
				LocalDate oneYearBefore = last.getStockPriceId().getInputDate().minusYears(1);

				List<StockPrice> stockPriceOneYearBefore = stockPriceService.getPriceForPeriod(st, oneYearBefore,
						oneYearBefore);

				List<StockPerformance> listStockPerformancePreviousReview = getPerformanceForReview(reviews.getValue2(),
						listMeasureToExecute,
						stockPriceOneYearBefore != null && !stockPriceOneYearBefore.isEmpty()
								? stockPriceOneYearBefore.get(0)
								: null, st);
			}

		} catch (FunctionalException e) {
			throw e;

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}

		return null;
	}

	private List<StockPerformance> getPerformanceForReview(Review review, List<Measure> listMeasureToExecute,
			StockPrice stockPrice, StockTicker st) {
		List<StockPerformance> listPerformances = new ArrayList<>();
		for (Measure m : listMeasureToExecute) {
			StockPerformance spm = new StockPerformance();
			try {
				spm.setMeasure(m.getCode());
				spm.setYear(Joiner.on("-").join(review.getStartYear().toString(),
						Optional.ofNullable(review.getEndYear()).orElse(null).toString()));
				spm.setPeriod(review.getPeriod());

				switch (m.getCode()) {
				case "pe":
					spm.setValue(valuationService.getPriceEarning(
							getStockPrice(stockPrice, "error.finance.performance.pe"), review.getNbSharesEndPeriod(),
							Optional.ofNullable(review.getOperations())
									.orElseThrow(() -> new FunctionalException(this, KEY_PERFORMANCE_REQUIRED,
											new String[] { getProperty("error.finance.performance.operations"),
													getProperty("error.finance.performance.pe") }))
									.getShareownersEarnings(),
							PeriodEnum.valueOf(review.getPeriod()), st.getPlace().getCurrency(), review.getCurrency() ));
					break;
				}

			} catch (FunctionalException e) {
				spm.setErrors(propertiesTools.getProperty(e.getKeyError(), e.getArgs()));
			}
			listPerformances.add(spm);
		}
		return listPerformances;
	}

	/**
	 * Récupère le prix ou lève une excetion fonctionnelle en cas d'absence
	 * 
	 * @param stockPrice
	 *            prix de l'action
	 * @param properties
	 *            propriété à afficher en cas d'erreur
	 * @return stockPrice si non null
	 */
	private StockPrice getStockPrice(StockPrice stockPrice, String properties) {
		if (stockPrice == null) {
			throw new FunctionalException(this, "error.finance.performance.price",
					new String[] { getProperty("error.finance.performance.pe") });
		}
		return stockPrice;
	}

	private DoubleReturnValue<Review, Review> getReviews(Integer startYear, Integer endYear, PeriodEnum period,
			Integer previousStartYear, Integer previousEndYear, PeriodEnum previousPeriod, StockTicker st) {
		List<Review> listReview = reviewDao.findReview(st.getStock().getId(), null, startYear, endYear, period);

		if (listReview.isEmpty()) {
			throw new FunctionalException(this, "error.finance.review.notfound", new String[] {
					startYear.toString().concat("-" + Optional.ofNullable(endYear).orElse(startYear).toString()),
					period.name() });
		}

		Review initReview = listReview.get(0);

		Review previousReview = null;
		listReview = new ArrayList<>();

		if (previousStartYear != null) {
			listReview = reviewDao.findReview(st.getStock().getId(), null, previousStartYear, previousEndYear,
					previousPeriod);
		}

		if (!listReview.isEmpty()) {
			previousReview = listReview.get(0);
		}
		return new DoubleReturnValue<Review, Review>(initReview, previousReview);
	}

	/**
	 * Filtre les mesures en fonction des paramètres de l'utilisateur
	 * 
	 * @param filter
	 *            liste des filtes
	 * @return les mesures correspondantes
	 */
	private List<Measure> filterMeasure(List<String> filter) {
		Set<String> transformedFilter = filter.stream().map(String::trim).map(String::toLowerCase)
				.collect(Collectors.toSet());
		return getListMeasure().stream().filter(m -> transformedFilter.contains(m.getCode().toLowerCase()))
				.collect(Collectors.toList());
	}

	/**
	 * Récupère toutes les mesures de la base
	 * 
	 * @return la liste des mesures
	 */
	private List<Measure> getListMeasure() {
		if (listMeasure == null) {
			listMeasure = measureDao.findAll();
		}
		return listMeasure;
	}

	/**
	 * Simplication of property access
	 * 
	 * @param key
	 *            the key
	 * @return text of the property
	 */
	private String getProperty(String key) {
		return propertiesTools.getProperty(key);
	}

}
