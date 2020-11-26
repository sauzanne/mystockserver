package fr.mystocks.mystockserver.service.finance.valuation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.valuation.ValuationDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.valuation.Valuation;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@Service("valuationService")
@Transactional
public class ValuationServiceImpl implements ValuationService {

	@Autowired
	private ValuationDao<Valuation> valuationDao;

	@Autowired
	private AccountDao<Account> accountDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Integer storeValuation(String token, Integer id, BigDecimal expectedGrowth) {
		LocalDateTime now = LocalDateTime.now();
		try {

			Account account = accountDao.getAccountByToken(token);

			// si le compte n'est pas identifié on sort de la fonction
			if (account == null) {
				return null;
			}

			Valuation valuation;
			if (id == null || id == 0) {
				valuation = new Valuation();
			} else {
				valuation = valuationDao.findById(id);
			}


			valuation.setExpectedGrowth(expectedGrowth);
			
			if (id == null || id == 0) {
				valuation.setFirstInput(now);
				valuationDao.create(valuation);
			} else {
				valuation.setLastModified(now);
				valuationDao.update(valuation);
			}
			return valuation.getId();
		} catch (RuntimeException e) {

			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
