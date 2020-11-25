package fr.mystocks.mystockserver.service.finance.liabilities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.liabilities.LiabilitiesDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.liabilities.Liabilities;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@Service("liabilitiesService")
@Transactional
public class LiabilitiesServiceImpl implements LiabilitiesService {

	@Autowired
	private LiabilitiesDao<Liabilities> liabilitiesDao;

	@Autowired
	private AccountDao<Account> accountDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Integer storeLiabilities(String token, Integer id, BigDecimal currentLiabilities,
			BigDecimal shortTermBorrowings, BigDecimal longTermBorrowings, BigDecimal capitalLeases) {
		LocalDateTime now = LocalDateTime.now();
		try {

			Account account = accountDao.getAccountByToken(token);

			// si le compte n'est pas identifié on sort de la fonction
			if (account == null) {
				return null;
			}

			Liabilities liabilities;
			if (id == null || id == 0) {
				liabilities = new Liabilities();
			} else {
				liabilities = liabilitiesDao.findById(id);
			}

			liabilities.setCurrentLiabilities(currentLiabilities);
			liabilities.setShortTermBorrowings(shortTermBorrowings);
			liabilities.setLongTermBorrowings(longTermBorrowings);
			liabilities.setCapitalLeases(capitalLeases);

			if (id == null || id == 0) {
				liabilities.setFirstInput(now);
				liabilitiesDao.create(liabilities);
			} else {
				liabilities.setLastModified(now);
				liabilitiesDao.update(liabilities);
			}
			return liabilities.getId();
		} catch (RuntimeException e) {

			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
