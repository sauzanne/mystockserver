package fr.mystocks.mystockserver.service.security;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@Transactional
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountDao<Account> accountDao;

	@Autowired
	private SecurityService securityService;

	@Override
	public Account authenticate(String user, String password) {
		try {
			Account account = accountDao.authenticate(user, securityService.encryptInSHA(password));

			if (account != null) {
				account.setToken(securityService.generateToken());
				account.setLastConnection(LocalDateTime.now());
				account.setLastModified(LocalDateTime.now());
				accountDao.update(account);
			}
			return account;
		} catch (RuntimeException | NoSuchAlgorithmException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

	@Override
	public Account authenticateWithToken(String token) {
		try {
			
			Account account = accountDao.getAccountByToken(token);

			if (account != null) {
				account.setLastConnection(LocalDateTime.now());
				accountDao.update(account);
			}
			return account;
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

}
