/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.amf.notification;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.amf.notification.Notification;
import fr.mystocks.mystockserver.data.security.Account;

/**
 * @author sauzanne
 *
 */
@Repository("notificationDao")
public class NotificationDaoImpl extends AbstractDaoImpl<Notification> implements NotificationDao<Notification> {

	private static final String BIND_STOCK_ID = "stockId";
	private static final String BIND_ACCOUNT_ID = "accountId";

	@Override
	public List<Account> findSubscriber(Integer stockId, Integer accountId) {
		StringBuilder request = new StringBuilder();

		request.append("select a ");
		
		return (List<Account>) requestNotificationByStockAndAccount(stockId, accountId, request).list();
	}

	private Query requestNotificationByStockAndAccount(Integer stockId, Integer accountId, StringBuilder request) {
		request.append(" from Notification n ");
		request.append(" inner join n.account a ");
		request.append(" inner join n.stock s ");

		request.append(" where s.id=:" + BIND_STOCK_ID);

		if (accountId != null) {
			request.append(" and a.id=:" + BIND_ACCOUNT_ID);

		}

		Query query = getSession().createQuery(request.toString());

		query.setParameter(BIND_STOCK_ID, stockId);

		if (accountId != null) {
			query.setParameter(BIND_ACCOUNT_ID, accountId);
		}
		return query;
	}
	
	@Override
	public List<Notification> findNotification(Integer stockId, Integer accountId) {
		StringBuilder request = new StringBuilder();

		request.append("select n ");

		return (List<Notification>) requestNotificationByStockAndAccount(stockId, accountId, request).list();
	}


}
