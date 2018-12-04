package fr.mystocks.mystockserver.dao.security;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.security.User;

public interface SessionDao<T> extends Dao<T> {

	List<T> findByUser(User user);
	
	T findByToken(String token);

}
