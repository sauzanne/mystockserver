/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.place;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.place.Place;

/**
 * @author sauzanne
 *
 */
@Repository("placeDao")
public class PlaceDaoImpl extends AbstractDaoImpl<Place> implements PlaceDao<Place> {



	@Override
	public Place findByCode(String code) {
		Criteria criteria = getSession().createCriteria(Place.class);
		criteria.add(Restrictions.eq("code",code));
		return (Place) criteria.uniqueResult();
	}


}
