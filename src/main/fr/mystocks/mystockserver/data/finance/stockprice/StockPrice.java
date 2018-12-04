package fr.mystocks.mystockserver.data.finance.stockprice;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mystocks.stock_price")
public class StockPrice implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3985180131628854532L;

	@EmbeddedId
    private StockPriceId stockPriceId;

    @Column(name = "close")
    private Boolean close;

    @Column(name = "price")
    private BigDecimal price;

    /**
     * @author sauzanne @return the stockPriceId
     */
    public StockPriceId getStockPriceId() {
	return stockPriceId;
    }

    /**
     * @author sauzanne @param stockPriceId the stockPriceId to set
     */
    public void setStockPriceId(StockPriceId stockPriceId) {
	this.stockPriceId = stockPriceId;
    }

    /**
     * @author sauzanne @return the close
     */
    public Boolean getClose() {
	return close;
    }

    /**
     * @author sauzanne @param close the close to set
     */
    public void setClose(Boolean close) {
	this.close = close;
    }

    /**
     * @author sauzanne @return the price
     */
    public BigDecimal getPrice() {
	return price;
    }

    /**
     * @author sauzanne @param price the price to set
     */
    public void setPrice(BigDecimal price) {
	this.price = price;
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
	result = prime * result + ((close == null) ? 0 : close.hashCode());
	result = prime * result + ((price == null) ? 0 : price.hashCode());
	result = prime * result + ((stockPriceId == null) ? 0 : stockPriceId.hashCode());
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
	StockPrice other = (StockPrice) obj;
	if (close == null) {
	    if (other.close != null)
		return false;
	} else if (!close.equals(other.close))
	    return false;
	if (price == null) {
	    if (other.price != null)
		return false;
	} else if (!price.equals(other.price))
	    return false;
	if (stockPriceId == null) {
	    if (other.stockPriceId != null)
		return false;
	} else if (!stockPriceId.equals(other.stockPriceId))
	    return false;
	return true;
    }

}
