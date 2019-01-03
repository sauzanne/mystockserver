/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.stockticker;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

/**
 * @author sauzanne
 *
 */
@Repository("stockTickerDao")
public class StockTickerDaoImpl extends AbstractDaoImpl<StockTicker> implements StockTickerDao<StockTicker> {

	private static final String BIND_CODE = "code";
	private static final String BIND_CODE_PLACE = "codePlace";

	@Override
	public StockTicker findByCodeAndPlace(String code, String codePlace, boolean join) {
		StringBuilder request = new StringBuilder();

		request.append("select st from StockTicker st");

		if (join) {
			request.append(" join fetch st.stock s ");
			request.append(" join fetch st.place p ");
		}

		request.append(" where st.code=:" + BIND_CODE);
		if (!Strings.isNullOrEmpty(codePlace)) {
			request.append(" and st.place.code=:" + BIND_CODE_PLACE);
		}

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_CODE, code);
		if (!Strings.isNullOrEmpty(codePlace)) {
			query.setParameter(BIND_CODE_PLACE, codePlace);
		}

		return (StockTicker) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockTicker> findAllEnableStockTicker() {
		Criteria criteria = getSession().createCriteria(StockTicker.class);
		criteria.add(Restrictions.eqOrIsNull("disabled", null));
		return criteria.list();
	}


}
