package fr.mystocks.mystockserver.service.finance.operations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.finance.operations.OperationsDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.operations.Operations;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;


@Transactional
@Service("operationsService")
public class OperationsServiceImpl implements OperationsService {

	@Autowired
	private OperationsDao<Operations> operationsDao;
	
	@Autowired
	private AccountDao<Account> accountDao;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Integer storeOperations(String token, Integer id, BigDecimal revenues, BigDecimal ebit, BigDecimal currentEbit,
			BigDecimal ebitda, BigDecimal costOfRevenues, BigDecimal financialExpenses, BigDecimal incomeTaxes, BigDecimal shareownersEarnings, BigDecimal adjustedEarnings,
			BigDecimal operationalCashFlow, BigDecimal freeCashFlow, BigDecimal exceptionalItems) {
		LocalDateTime now = LocalDateTime.now();
		try {
			
			Account account = accountDao.getAccountByToken(token);

			// si le compte n'est pas identifié on sort de la fonction
			if (account == null) {
				return null;
			}


			Operations operations;
			if (id == null || id == 0) {
				operations = new Operations();
			} else {
				operations = operationsDao.findById(id);
			}
			operations.setRevenues(revenues);
			operations.setCostOfRevenues(costOfRevenues);
			operations.setCurrentEbit(currentEbit);
			operations.setEbit(ebit);
			operations.setEbitda(ebitda);
			operations.setFinancialExpenses(financialExpenses);
			operations.setIncomeTaxes(incomeTaxes);
			operations.setExceptionalItems(exceptionalItems);
			operations.setFreeCashFlow(freeCashFlow);
			operations.setOperationalCashFlow(operationalCashFlow);
			operations.setShareownersEarnings(shareownersEarnings);
			operations.setAdjustedEarnings(adjustedEarnings);
			if (id == null || id == 0) {
				operations.setFirstInput(now);
				operationsDao.create(operations);
			} else {
				operations.setLastModified(now);
				operationsDao.update(operations);
			}
			return operations.getId();
		} catch (RuntimeException e) {

			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
