package fr.mystocks.mystockserver.dao.security.account;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.security.Account;

public interface AccountDao<T> extends Dao<T> {
    
	Account getAccountByLogin(String login);

	Account authenticate(String login, String password);
	
	Account getAccountByToken(String token);

	
}
