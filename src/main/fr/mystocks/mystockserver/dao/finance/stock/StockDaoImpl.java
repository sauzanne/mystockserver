/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.stock;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.stock.Stock;

/**
 * @author sauzanne
 *
 */
@Repository("stockDao")
public class StockDaoImpl extends AbstractDaoImpl<Stock> implements StockDao<Stock> {

	@Override
	public Stock findByIsin(String isin) {
		Criteria criteria = getSession().createCriteria(Stock.class);
		criteria.add(Restrictions.eq("isin", isin));
		return (Stock) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Stock> findByName(String name) {
		Criteria criteria = getSession().createCriteria(Stock.class);
		criteria.add(Restrictions.ilike("name", "%" + name + "%", MatchMode.ANYWHERE));
		return (List<Stock>) criteria.list();
	}

}
