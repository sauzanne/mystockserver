/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.stockprice;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

/**
 * @author sauzanne
 *
 */
@Repository("stockPriceDao")
public class StockPriceDaoImpl extends AbstractDaoImpl<StockPrice> implements StockPriceDao<StockPrice> {

	private final static String BIND_INPUT_DATE = "inputDate";
	private final static String BIND_CODE = "code";
	private final static String BIND_CODE_PLACE = "codePlace";
	private final static String BIND_START_DATE = "startDate";
	private final static String BIND_END_DATE = "endDate";

	@SuppressWarnings("unchecked")
	@Override
	public List<StockPrice> findByDateRange(StockTicker st, LocalDate startDate, LocalDate endDate) {

		StringBuilder request = new StringBuilder();

		request.append("select sp from StockPrice sp");
		request.append(" join fetch sp.stockPriceId.stockTicker st ");

		request.append(" where sp.stockPriceId.inputDate >= :" + BIND_START_DATE + " and sp.stockPriceId.inputDate <= :"
				+ BIND_END_DATE + " and st.code = :" + BIND_CODE + " and st.place.code=:" + BIND_CODE_PLACE + " ");

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_CODE, st.getCode());
		query.setParameter(BIND_CODE_PLACE, st.getPlace().getCode());
		query.setParameter(BIND_START_DATE, startDate);
		query.setParameter(BIND_END_DATE, endDate);

		// Criteria criteria = getSession().createCriteria(StockPrice.class);
		// criteria.add(Restrictions.eq("stockTicker", st));
		// criteria.add(Restrictions.between("inputDate", startDate, endDate));
		return query.list();
	}

	@Override
	public StockPrice findAtDate(StockTicker st, LocalDate date) {
		StringBuilder request = new StringBuilder();

		request.append("select sp from StockPrice sp");
		request.append(" join fetch sp.stockPriceId.stockTicker st ");

		request.append(" where sp.stockPriceId.inputDate = :" + BIND_INPUT_DATE + " and st.code = :" + BIND_CODE
				+ " and st.place.code=:" + BIND_CODE_PLACE + " ");

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_CODE, st.getCode());
		query.setParameter(BIND_CODE_PLACE, st.getPlace().getCode());
		query.setParameter(BIND_INPUT_DATE, date);

		// TODO vérifier le résultat avec des données dans la base
		// Criteria criteria = getSession().createCriteria(StockPriceId.class);
		// //a
		// criteria.add(Restrictions.eq("inputDate", date));
		// //criteria.createAlias("stockPriceId.stockTicker", "st");
		// //criteria.add(Restrictions.eq("stockPriceId.stockTicker", st));
		//
		// criteria.add(Restrictions.eq("stockTicker.code", st.getCode()));
		return (StockPrice) query.uniqueResult();
	}

}
