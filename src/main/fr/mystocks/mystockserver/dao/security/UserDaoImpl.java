/**
 * 
 */
package fr.mystocks.mystockserver.dao.security;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.security.User;

/**
 * @author sauzanne
 *
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao<User> {

	@Override
	public User authenticate(String login, String password) {

		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("login", login));
		criteria.add(Restrictions.eq("password", password));
		return (User) criteria.uniqueResult();
	}

}
