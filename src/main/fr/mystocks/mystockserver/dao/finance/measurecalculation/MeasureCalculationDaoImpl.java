/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.measurecalculation;

import java.time.LocalDate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.measurecalculation.MeasureCalculation;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

/**
 * @author sauzanne
 *
 */
@Repository("measureCalculationDao")
public class MeasureCalculationDaoImpl extends AbstractDaoImpl<MeasureCalculation>
		implements MeasureCalculationDao<MeasureCalculation> {

	private static final String BIND_STOCKTICKER_ID = "stockTickerId";
	private static final String BIND_MEASURE_ID = "measureId";
	private static final String BIND_CALCULATION_DATE = "calculationDate";
	private static final String BIND_REVIEW = "review";

	@Override
	public MeasureCalculation findLastMeasureCalculation(StockTicker st, Measure m) {
		StringBuilder request = new StringBuilder();

		request.append("select mc from MeasureCalculation mc");

		request.append(" where mc.stockTicker.id=:" + BIND_STOCKTICKER_ID);
		request.append(" and mc.measure.id=:" + BIND_MEASURE_ID);
		request.append(" order by mc.calculationDate desc");

		Query query = getSession().createQuery(request.toString());
		query.setMaxResults(1);

		query.setParameter(BIND_STOCKTICKER_ID, st.getId());
		query.setParameter(BIND_MEASURE_ID, m.getId());

		return (MeasureCalculation) query.uniqueResult();
	}

	@Override
	public MeasureCalculation findMeasureCalculation(StockTicker st, Measure m, LocalDate calculationDate, Review r) {
		StringBuilder request = new StringBuilder();

		request.append("select mc from MeasureCalculation mc");

		request.append(" where mc.stockTicker.id=:" + BIND_STOCKTICKER_ID);
		request.append(" and mc.measure.id=:" + BIND_MEASURE_ID);
		request.append(" and mc.calculationDate=:" + BIND_CALCULATION_DATE);

		if (r != null) {
			request.append(" and mc.review.id=:" + BIND_REVIEW);

		}

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_STOCKTICKER_ID, st.getId());
		query.setParameter(BIND_MEASURE_ID, m.getId());
		query.setParameter(BIND_CALCULATION_DATE, calculationDate);

		if (r != null) {
			query.setParameter(BIND_REVIEW, r.getId());
		}

		return (MeasureCalculation) query.uniqueResult();

	}

}
