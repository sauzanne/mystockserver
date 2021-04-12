package fr.mystocks.mystockserver.dao.finance.amf.notification;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.amf.notification.Notification;
import fr.mystocks.mystockserver.data.security.Account;

public interface NotificationDao<T> extends Dao<T> {

	List<Account> findSubscriber(Integer stockId, Integer accountId);

	List<Notification> findNotification(Integer stockId, Integer accountId);


}
