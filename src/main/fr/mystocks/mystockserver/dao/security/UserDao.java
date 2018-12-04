package fr.mystocks.mystockserver.dao.security;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.security.User;

public interface UserDao<T> extends Dao<T> {
	
	User authenticate(String login, String password);

}
