package fr.mystocks.mystockserver.view.model.finance.amf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class AmfStockModel {

	String stockIsin;
	String stockTicker;

	String stockName;

	/**
	 * @return the stockIsin
	 */
	public String getStockIsin() {
		return stockIsin;
	}

	/**
	 * @param stockIsin the stockIsin to set
	 */
	public void setStockIsin(String stockIsin) {
		this.stockIsin = stockIsin;
	}
	
	/**
	 * @return the stockTicker
	 */
	public String getStockTicker() {
		return stockTicker;
	}

	/**
	 * @param stockTicker the stockTicker to set
	 */
	public void setStockTicker(String stockTicker) {
		this.stockTicker = stockTicker;
	}


	/**
	 * @return the stockName
	 */
	public String getStockName() {
		return stockName;
	}

	/**
	 * @param stockName the stockName to set
	 */
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}


	public AmfStockModel(String stockIsin, String stockTicker, String stockName) {
		super();
		this.stockIsin = stockIsin;
		this.stockName = stockName;
		this.stockTicker = stockTicker;
	}

}
