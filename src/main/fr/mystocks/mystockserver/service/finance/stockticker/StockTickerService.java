package fr.mystocks.mystockserver.service.finance.stockticker;

import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.data.security.User;

public interface StockTickerService {

    Integer createStockTicker(String isin, String code, Integer place, User user, boolean mainPlace, boolean byPassPriceVerification);
    
    
    StockTicker getStockTicker(String code, String placeCode);


}
