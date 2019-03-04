/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.measure;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.measure.Measure;

/**
 * @author sauzanne
 *
 */
@Repository("measureDao")
public class MeasureDaoImpl extends AbstractDaoImpl<Measure> implements MeasureDao<Measure> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Measure> getMeasuresByCode(List<String> listCodes) {
		Criteria criteria = getSession().createCriteria(Measure.class);
		criteria.add(Restrictions.in("code", listCodes));
		return criteria.list();
	}
	



}
