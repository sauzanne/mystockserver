package fr.mystocks.mystockserver.service.finance.liabilities;

import java.math.BigDecimal;

public interface LiabilitiesService {
    
    Integer storeLiabilities(String token, Integer id, BigDecimal currentLiabilities, BigDecimal shortTermBorrowings, BigDecimal longTermBorrowings, BigDecimal capitalLeases);

}
