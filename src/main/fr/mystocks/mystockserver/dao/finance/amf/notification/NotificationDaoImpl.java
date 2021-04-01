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

	@Override
	public List<Account> findSubsriberByStock(Integer stockId) {
		StringBuilder request = new StringBuilder();

		request.append("select a from Notification n ");
		request.append(" inner join n.account a ");
		request.append(" inner join n.stock s ");



		request.append(" where s.id=:" + BIND_STOCK_ID);

		Query query = getSession().createQuery(request.toString());
		
		query.setParameter(BIND_STOCK_ID, stockId);

		return (List<Account>)query.list();
	}

}
