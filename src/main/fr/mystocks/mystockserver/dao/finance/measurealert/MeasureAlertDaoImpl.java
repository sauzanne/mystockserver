/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.measurealert;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.measurealert.MeasureAlert;

/**
 * @author sauzanne
 *
 */
@Repository("measureAlertDao")
public class MeasureAlertDaoImpl extends AbstractDaoImpl<MeasureAlert> implements MeasureAlertDao<MeasureAlert> {

	private static final String BIND_ACCOUNT_ID = "accountId";
	private static final String BIND_STOCKTICKER_ID = "stockTickerId";
	private static final String BIND_MEASURE_ID = "measureId";
	private static final String BIND_MEASURE_COMPARED_ID = "measureComparedId";
	private static final String BIND_BINARY_OPERATOR = "binaryOperator";
	private static final String WHERE = " where ";
	private static final String AND = " and ";
	private static final String BIND_TRIGGERED = "triggered";
	private static final String BIND_VALUE = "value";

	@SuppressWarnings("unchecked")
	@Override
	public List<MeasureAlert> getNonTriggeredMeasures() {
		Criteria criteria = getSession().createCriteria(MeasureAlert.class);
		criteria.add(Restrictions.in("triggered", false));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MeasureAlert> findMeasureAlert(Integer accountId, Integer stockTickerId, Integer measureId,
			Integer measureIdCompared, BigDecimal value, String binaryOperator, Boolean triggered) {
		StringBuilder request = new StringBuilder();

		request.append("select ma from MeasureAlert ma ");

		boolean isWherePresent = request.lastIndexOf("where") != -1;

		if (accountId != null) {
			request.append(" where ma.account.id=:" + BIND_ACCOUNT_ID);
		}

		if (stockTickerId != null) {
			request.append((isWherePresent ? WHERE : AND) + " ma.stockTicker.id=:" + BIND_STOCKTICKER_ID);
		}
		if (measureId != null) {
			request.append((isWherePresent ? WHERE : AND) + " ma.measure.id=:" + BIND_MEASURE_ID);
		}
		if (measureIdCompared != null) {
			request.append((isWherePresent ? WHERE : AND) + " ma.measureCompared.id=:" + BIND_MEASURE_COMPARED_ID);
		}
		if (!Strings.isNullOrEmpty(binaryOperator)) {
			request.append((isWherePresent ? WHERE : AND) + " ma.binaryOperator=:" + BIND_BINARY_OPERATOR);
		}
		if (triggered != null) {
			request.append((isWherePresent ? WHERE : AND) + " ma.triggered=:" + BIND_TRIGGERED);
		}
		if (value != null) {
			request.append((isWherePresent ? WHERE : AND) + " ma.value=:" + BIND_VALUE);
		}

		request.append(" order by ma.stockTicker.code, ma.measure.code ");

		Query query = getSession().createQuery(request.toString());

		if (accountId != null) {
			query.setParameter(BIND_ACCOUNT_ID, accountId);
		}
		if (stockTickerId != null) {
			query.setParameter(BIND_STOCKTICKER_ID, stockTickerId);
		}
		if (measureId != null) {
			query.setParameter(BIND_MEASURE_ID, measureId);
		}
		if (measureIdCompared != null) {
			query.setParameter(BIND_MEASURE_COMPARED_ID, measureIdCompared);
		}
		if (!Strings.isNullOrEmpty(binaryOperator)) {
			query.setParameter(BIND_BINARY_OPERATOR, binaryOperator);
		}
		if (triggered != null) {
			query.setParameter(BIND_TRIGGERED, triggered);
		}
		if (value != null) {
			query.setParameter(BIND_VALUE, value);
		}

		return (List<MeasureAlert>) query.list();

	}
}
