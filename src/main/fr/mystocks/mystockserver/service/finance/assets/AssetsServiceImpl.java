package fr.mystocks.mystockserver.service.finance.assets;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.assets.AssetsDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.assets.Assets;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@Service("assetsService")
@Transactional
public class AssetsServiceImpl implements AssetsService {

	@Autowired
	private AssetsDao<Assets> assetsDao;

	@Autowired
	private AccountDao<Account> accountDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
    public Integer storeAssets(String token, Integer id, BigDecimal cash, BigDecimal inventories, BigDecimal currentAssets, BigDecimal goodWill, BigDecimal tradeAccounts) {
		
		LocalDateTime now = LocalDateTime.now();
		try {

			Account account = accountDao.getAccountByToken(token);

			// si le compte n'est pas identifié on sort de la fonction
			if (account == null) {
				return null;
			}

			Assets assets;
			if (id == null || id == 0) {
				assets = new Assets();
			} else {
				assets = assetsDao.findById(id);
			}

			assets.setCash(cash);
			assets.setInventories(inventories);
			assets.setCurrentAssets(currentAssets);
			assets.setGoodwill(goodWill);
			assets.setTradeAccounts(tradeAccounts);


			if (id == null || id == 0) {
				assets.setFirstInput(now);
				assetsDao.create(assets);
			} else {
				assets.setLastModified(now);
				assetsDao.update(assets);
			}
			return assets.getId();
		} catch (RuntimeException e) {

			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
