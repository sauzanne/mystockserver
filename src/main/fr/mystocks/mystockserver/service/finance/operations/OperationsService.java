package fr.mystocks.mystockserver.service.finance.operations;

import java.math.BigDecimal;

public interface OperationsService {

	/**
	 * Store an operations
	 * @param token
	 * @param id
	 * @param revenues
	 * @param ebit
	 * @param currentEbit
	 * @param ebitda
	 * @param costOfRevenues
	 * @param financialExpenses
	 * @param shareownersEarnings
	 * @param adjustedEarnings
	 * @param operationalCashFlow
	 * @param freeCashFlow
	 * @param exceptionalItems
	 * @return id of the stored operations
	 */
	Integer storeOperations(String token, Integer id, BigDecimal revenues, BigDecimal ebit, BigDecimal currentEbit,
			BigDecimal ebitda, BigDecimal costOfRevenues, BigDecimal financialExpenses, BigDecimal shareownersEarnings,
			BigDecimal adjustedEarnings, BigDecimal operationalCashFlow, BigDecimal freeCashFlow,
			BigDecimal exceptionalItems);

}
