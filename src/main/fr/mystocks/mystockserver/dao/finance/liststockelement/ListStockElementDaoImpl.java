/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.liststockelement;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.liststockelement.ListStockElement;

/**
 * @author sauzanne
 *
 */
@Repository("listStockElementDao")
public class ListStockElementDaoImpl extends AbstractDaoImpl<ListStockElement>
		implements ListStockElementDao<ListStockElement> {

	private static String BIND_LOGIN = "login";
	private static String BIND_IDS_TO_DELETE = "idsToDelete";
	private static String BIND_ID_LIST_STOCK = "idListStock";

	@Override
	public Integer deleteElements(String login, List<Integer> idsToDelete) {
		StringBuilder request = new StringBuilder();

		/*
		 * Note importante MySql n'accepte pas que la table supprimée ou modifiée soit
		 * aussi dans le from de la sous-requête
		 * https://stackoverflow.com/questions/17861259/can-i-execute-a-batch-delete-
		 * with-join-statements
		 */
		request.append("delete ListStockElement lse ");
		request.append(" WHERE id in (select lse.id from lse.listStock ls ");
		request.append(" join ls.account ac ");
		request.append(" WHERE ac.login =:" + BIND_LOGIN + ")");
		request.append(" and lse.stock.id in (:" + BIND_IDS_TO_DELETE + ")");

		/*
		 * Note Importante pour les SQL Query il faut absolument pouvoir sélectionner la
		 * base, sinon MySQL renvoit un 'no database selected'
		 */

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_LOGIN, login);
		query.setParameterList(BIND_IDS_TO_DELETE, idsToDelete);

		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ListStockElement> findByListStockId(Integer idListStock) {
		StringBuilder request = new StringBuilder();

		request.append(" from ListStockElement lse ");
		request.append(" join fetch lse.listStock ls ");
		request.append(" WHERE ls.id =:" + BIND_ID_LIST_STOCK);

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_ID_LIST_STOCK, idListStock);

		return (List<ListStockElement>) query.list();
	}

}
