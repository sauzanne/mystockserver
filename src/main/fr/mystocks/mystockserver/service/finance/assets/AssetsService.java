package fr.mystocks.mystockserver.service.finance.assets;

import java.math.BigDecimal;

public interface AssetsService {
    
    Integer storeAssets(String token, Integer id, BigDecimal cash, BigDecimal inventories, BigDecimal currentAssets, BigDecimal goodWill, BigDecimal tradeAccounts);

}
