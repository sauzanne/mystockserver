package fr.mystocks.mystockserver.service.finance.valuation;

import java.math.BigDecimal;

public interface ValuationService {
    
    Integer storeValuation(String token, Integer id, BigDecimal expectedGrowth);

}
