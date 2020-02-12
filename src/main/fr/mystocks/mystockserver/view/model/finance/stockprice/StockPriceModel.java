package fr.mystocks.mystockserver.view.model.finance.stockprice;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.technic.serializer.LocalDateSerializer;
import fr.mystocks.mystockserver.view.model.finance.stockticker.StockTickerModel;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class StockPriceModel {

	@JsonProperty("price")
	private BigDecimal price;

	@JsonProperty("date")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate date;

	@JsonProperty("close")
	private Boolean close;

	@JsonProperty
	private StockTickerModel stockTicker;

	/**
	 *
	 * @param price
	 * @param LocalDate
	 * @param close
	 */
	@JsonCreator
	public StockPriceModel(BigDecimal price, LocalDate LocalDate, Boolean close) {
		super();
		this.price = price;
		this.date = LocalDate;
		this.close = close;
	}

	@JsonCreator
	public StockPriceModel(BigDecimal price, LocalDate date, Boolean close, StockTicker stockTicker) {
		super();
		this.price = price;
		this.date = date;
		this.close = close;
		this.stockTicker = new StockTickerModel();
		this.stockTicker.convertFromStockTicker(stockTicker, false);
	}

	/**
	 *
	 */
	@JsonCreator
	public StockPriceModel() {
		super();
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

	/**
	 * @author sauzanne @return the LocalDate
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @author sauzanne @param LocalDate the LocalDate to set
	 */
	public void setDate(LocalDate LocalDate) {
		this.date = LocalDate;
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

	public void convertFromStockPrice(StockPrice sp) {
		BeanUtils.copyProperties(sp, this);
		// this.setDate(new
		// Date(sp.getStockPriceId().getInputDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)));
		this.setDate(sp.getStockPriceId().getInputDate());

		if (sp.getStockPriceId() != null && sp.getStockPriceId().getStockTicker() != null) {
			this.stockTicker = new StockTickerModel();
			this.stockTicker.convertFromStockTicker(sp.getStockPriceId().getStockTicker(), false);

		}

	}

}
