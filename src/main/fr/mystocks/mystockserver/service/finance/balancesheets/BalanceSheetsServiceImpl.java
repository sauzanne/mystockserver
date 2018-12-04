package fr.mystocks.mystockserver.service.finance.balancesheets;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.balancesheets.BalanceSheetsDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.Equities;
import fr.mystocks.mystockserver.data.finance.assets.Assets;
import fr.mystocks.mystockserver.data.finance.balancesheets.BalanceSheets;
import fr.mystocks.mystockserver.data.finance.liabilities.Liabilities;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@Service("balanceSheets")
@Transactional
public class BalanceSheetsServiceImpl implements BalanceSheetsService {

	@Autowired
	private BalanceSheetsDao<BalanceSheets> balanceSheetsDao;

	@Autowired
	private AccountDao<Account> accountDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Integer storeBalanceSheets(String token, Integer id, Integer assetsId, Integer liabilitiesId,
			Integer equitiesId) {
		LocalDateTime now = LocalDateTime.now();
		try {

			Account account = accountDao.getAccountByToken(token);

			// si le compte n'est pas identifié on sort de la fonction
			if (account == null) {
				return null;
			}

			BalanceSheets balanceSheets;
			if (id == null || id == 0) {
				balanceSheets = new BalanceSheets();
			} else {
				balanceSheets = balanceSheetsDao.findById(id);
			}

			if (assetsId != null) {
				balanceSheets.setAssets(new Assets(assetsId));
			}

			if (liabilitiesId != null) {
				balanceSheets.setLiabilities(new Liabilities(liabilitiesId));
			}

			if (equitiesId != null) {
				balanceSheets.setEquities(new Equities(equitiesId));
			}

			if (id == null || id == 0) {
				balanceSheets.setFirstInput(now);
				balanceSheetsDao.create(balanceSheets);
			} else {
				balanceSheets.setLastModified(now);
				balanceSheetsDao.update(balanceSheets);
			}
			return balanceSheets.getId();
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
