package fr.mystocks.mystockserver.dao.finance.amf.notification;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.security.Account;

public interface NotificationDao<T> extends Dao<T> {

	List<Account> findSubsriberByStock(Integer stockId);


}
