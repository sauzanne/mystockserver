package fr.mystocks.mystockserver.service.finance.stockprice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

public interface StockPriceService {

    StockPrice getLast(StockTicker st);

    List<StockPrice> getPriceForPeriod(StockTicker st, LocalDate start, LocalDate end, Boolean...repeat);

    /**
     * Vérifie si le ticket de cotation d'une action existe
     * 
     * @param s objet stock ticker qui doit comporter au moins un code et une place de cotation valide
     *            
     * @return vrai si ce ticker existe
     */
    boolean checkStocks(StockTicker st);

    /**
     * Récupère la cotation moyenne sur une période
     * 
     * @param sq
     *            cotation
     * @param start
     *            date de début
     * @param end
     *            date de fin
     * @return
     */
    BigDecimal getAveragePrice(StockTicker sq, LocalDate start,
	    LocalDate end);

	List<StockPrice> getLastForList(List<StockTicker> listOfStockTickers);

	List<StockPrice> getPreviousForList(List<StockTicker> listOfStockTickers);

	StockPrice getPrevious(StockTicker st);

}
