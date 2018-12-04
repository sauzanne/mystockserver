package fr.mystocks.mystockserver.data.finance.stockprice;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

@Embeddable
public class StockPriceId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6575172692511363397L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_ticker_id", nullable = false)
    private StockTicker stockTicker;

    @Column(name = "input_date")
    private LocalDate inputDate;

    /**
     * @author sauzanne @return the stockTicker
     */
    public StockTicker getStockTicker() {
	return stockTicker;
    }

    /**
     * @author sauzanne @param stockTicker the stockTicker to set
     */
    public void setStockTicker(StockTicker stockTicker) {
	this.stockTicker = stockTicker;
    }

    /**
     * @author sauzanne @return the inputDate
     */
    public LocalDate getInputDate() {
	return inputDate;
    }

    /**
     * @author sauzanne @param inputDate the inputDate to set
     */
    public void setInputDate(LocalDate inputDate) {
	this.inputDate = inputDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((inputDate == null) ? 0 : inputDate.hashCode());
	result = prime * result + ((stockTicker == null) ? 0 : stockTicker.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	StockPriceId other = (StockPriceId) obj;
	if (inputDate == null) {
	    if (other.inputDate != null)
		return false;
	} else if (!inputDate.equals(other.inputDate))
	    return false;
	if (stockTicker == null) {
	    if (other.stockTicker != null)
		return false;
	} else if (!stockTicker.equals(other.stockTicker))
	    return false;
	return true;
    }

}
