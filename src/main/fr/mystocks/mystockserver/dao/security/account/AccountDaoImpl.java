package fr.mystocks.mystockserver.dao.security.account;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.security.Account;

@Repository("accountDao")
public class AccountDaoImpl extends AbstractDaoImpl<Account> implements AccountDao<Account> {

	@Override
	public Account getAccountByLogin(String login) {
		Criteria criteria = getSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("login", login));
		return (Account) criteria.uniqueResult();
	}

	@Override
	public Account authenticate(String login, String password) {
		Criteria criteria = getSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("login", login));
		criteria.add(Restrictions.eq("password", password));
		return (Account) criteria.uniqueResult();
	}

	@Override
	public Account getAccountByToken(String token) {
		Criteria criteria = getSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("token", token));
		return (Account) criteria.uniqueResult();
	}

}
