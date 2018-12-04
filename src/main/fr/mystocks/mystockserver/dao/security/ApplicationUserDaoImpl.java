/**
 * 
 */
package fr.mystocks.mystockserver.dao.security;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.security.ApplicationUser;
import fr.mystocks.mystockserver.data.security.User;

/**
 * @author sauzanne
 *
 */
@Repository("applicationUserDao")
public class ApplicationUserDaoImpl extends AbstractDaoImpl<ApplicationUser> implements ApplicationUserDao<ApplicationUser> {

    @SuppressWarnings("unchecked")
    @Override
    public List<ApplicationUser> findByUser(User user) {
	Criteria criteria = getSession().createCriteria(ApplicationUser.class);
//	ApplicationUserId applicationUserId = new ApplicationUserId();
//	applicationUserId.setUser(user);
//	applicationUserId.setApplication(new Application());
	criteria.add(Restrictions.eq("applicationUserId.user", user));
	return (List<ApplicationUser>) criteria.list();
    }


}
