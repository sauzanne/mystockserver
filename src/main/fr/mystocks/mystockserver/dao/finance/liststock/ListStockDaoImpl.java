/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.liststock;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.liststock.ListStock;

/**
 * @author sauzanne
 *
 */
@Repository("listStockDao")
public class ListStockDaoImpl extends AbstractDaoImpl<ListStock> implements ListStockDao<ListStock> {

	private static final String BIND_LOGIN = "login";
	private static final String BIND_ACCOUNT_ID = "id";


	@SuppressWarnings("unchecked")
	@Override
	public List<ListStock> getListsByLogin(String user) {
		StringBuilder request = new StringBuilder();

		request.append("from ListStock ls");
		request.append(" join fetch ls.account ac ");
		//request.append(" join fetch ls.listStockElement ");

		request.append(" where ac.login =:" + BIND_LOGIN);

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_LOGIN, user);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ListStock> getListsByAccount(Integer accountId) {
		StringBuilder request = new StringBuilder();

		request.append("from ListStock ls");
		request.append(" join fetch ls.account ac ");

		request.append(" where ac.id =:" + BIND_ACCOUNT_ID);

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_ACCOUNT_ID, accountId);
		return query.list();
	}


}
