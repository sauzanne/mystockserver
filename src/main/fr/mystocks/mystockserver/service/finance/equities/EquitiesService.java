package fr.mystocks.mystockserver.service.finance.equities;

import java.math.BigDecimal;

public interface EquitiesService {
    
    Integer storeEquities(String token, Integer id, BigDecimal shareHolderEquity, BigDecimal nonControllingInterest);

}
