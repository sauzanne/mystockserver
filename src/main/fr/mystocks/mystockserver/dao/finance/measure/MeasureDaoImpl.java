/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.measure;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.measurealert.MeasureAlert;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Measure> getAvailableMeasures() {
		Criteria criteria = getSession().createCriteria(Measure.class);
		criteria.add(Restrictions.eq("available", true));
		return criteria.list();
	}
	

	



}
