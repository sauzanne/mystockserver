/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.placeclosing;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.placeclosing.PlaceClosing;

/**
 * @author sauzanne
 *
 */
@Repository("placeClosingDao")
public class PlaceClosingDaoImpl extends AbstractDaoImpl<PlaceClosing> implements PlaceClosingDao<PlaceClosing> {

	private static final String BIND_CODE_PLACE = "codePlace";

	@SuppressWarnings("unchecked")
	@Override
	public List<PlaceClosing> findByCodePlace(String code) {
		StringBuilder request = new StringBuilder();

		request.append("select pc from PlaceClosing pc");
		request.append(" join fetch pc.place p ");

		request.append(" where p.code=:" + BIND_CODE_PLACE + " ");

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_CODE_PLACE, code);
		return query.list();
	}

}
