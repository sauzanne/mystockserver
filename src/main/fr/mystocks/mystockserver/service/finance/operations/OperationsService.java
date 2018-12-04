package fr.mystocks.mystockserver.service.finance.operations;

import java.math.BigDecimal;

public interface OperationsService {
    
    Integer storeOperations(String token, Integer id, BigDecimal revenues, BigDecimal ebit, BigDecimal currentEbit, BigDecimal ebitda, BigDecimal costOfRevenues, BigDecimal financialExpenses, BigDecimal shareownersEarnings,BigDecimal adjustedEarnings, BigDecimal operationalCashFlow, BigDecimal freeCashFlow, BigDecimal exceptionalItems);

}
