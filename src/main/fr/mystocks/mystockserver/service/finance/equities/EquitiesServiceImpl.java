package fr.mystocks.mystockserver.service.finance.equities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.equities.EquitiesDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.Equities;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@Transactional
@Service("equitiesService")
public class EquitiesServiceImpl implements EquitiesService {

	@Autowired
	private EquitiesDao<Equities> equitiesDao;

	@Autowired
	private AccountDao<Account> accountDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Integer storeEquities(String token, Integer id, BigDecimal shareHolderEquity, BigDecimal nonControllingInterests) {

		LocalDateTime now = LocalDateTime.now();
		try {

			Account account = accountDao.getAccountByToken(token);

			// si le compte n'est pas identifié on sort de la fonction
			if (account == null) {
				return null;
			}

			Equities equities;
			if (id == null || id == 0) {
				equities = new Equities();
			} else {
				equities = equitiesDao.findById(id);
			}

			equities.setShareholderEquity(shareHolderEquity);
			equities.setNonControllingInterests(nonControllingInterests);

			if (id == null || id == 0) {
				equities.setFirstInput(now);
				equitiesDao.create(equities);
			} else {
				equities.setLastModified(now);
				equitiesDao.update(equities);
			}
			return equities.getId();
		} catch (RuntimeException e) {

			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
