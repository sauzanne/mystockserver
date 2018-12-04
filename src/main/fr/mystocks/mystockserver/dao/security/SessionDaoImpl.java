/**
 * 
 */
package fr.mystocks.mystockserver.dao.security;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.security.Session;
import fr.mystocks.mystockserver.data.security.User;

/**
 * @author sauzanne
 *
 */
@Repository("sessionDao")
public class SessionDaoImpl extends AbstractDaoImpl<Session> implements SessionDao<Session> {

    @SuppressWarnings("unchecked")
    @Override
    public List<Session> findByUser(User user) {
	Criteria criteria = getSession().createCriteria(Session.class);
	criteria.add(Restrictions.eq("user", user));
	return (List<Session>) criteria.list();
    }

    @Override
    public Session findByToken(String token) {
	Criteria criteria = getSession().createCriteria(Session.class);
	criteria.add(Restrictions.eq("token", token));
	return (Session) criteria.uniqueResult();
    }

}
