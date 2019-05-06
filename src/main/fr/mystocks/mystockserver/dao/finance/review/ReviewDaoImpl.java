/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.review;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;

/**
 * @author sauzanne
 *
 */
@Repository("reviewDao")
public class ReviewDaoImpl extends AbstractDaoImpl<Review> implements ReviewDao<Review> {

	private final static String BIND_STOCK_ID = "stockId";
	private final static String BIND_TOKEN = "token";
	private final static String BIND_START_YEAR = "startYear";
	private final static String BIND_END_YEAR = "endYear";
	private static final String BIND_PERIOD = "period";

	@SuppressWarnings("unchecked")
	@Override
	public List<Review> findReview(Integer stockId, String token, Integer startYear, Integer endYear,
			PeriodEnum period) {
		StringBuilder request = new StringBuilder();

		request.append("select r from Review r ");
		request.append(" join fetch r.stock s ");
		request.append(" join fetch r.account a ");
		request.append("where s.id =:" + BIND_STOCK_ID);

		if (token != null) {
			request.append(" and a.token =:" + BIND_TOKEN);
		}

		if (startYear != null) {
			request.append(" and r.startYear =:" + BIND_START_YEAR);
		}

		if (endYear != null) {
			request.append(" and r.endYear =:" + BIND_END_YEAR);
		}

		if (period != null) {
			request.append(" and r.period =:" + BIND_PERIOD);
		}

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_STOCK_ID, stockId);
		if (token != null) {
			query.setParameter(BIND_TOKEN, token);
		}
		if (startYear != null) {
			query.setParameter(BIND_START_YEAR, startYear);
		}
		if (endYear != null) {
			query.setParameter(BIND_END_YEAR, endYear);
		}
		if (period != null) {
			query.setParameter(BIND_PERIOD, period.name());
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Review> findLastReview(Integer stockId) {
		StringBuilder request = new StringBuilder();

		request.append("select r from Review r ");
		request.append(" join fetch r.stock s ");
		request.append(" join fetch r.account a ");
		request.append("where s.id =:" + BIND_STOCK_ID);
		request.append(" order by r.startYear desc, r.endYear desc");



		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_STOCK_ID, stockId);
		return query.list();
	}

}
