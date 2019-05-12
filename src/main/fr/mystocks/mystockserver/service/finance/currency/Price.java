package fr.mystocks.mystockserver.service.finance.currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Price {

	private BigDecimal price;
	
	private LocalDateTime inputDate;

	public Price(BigDecimal price, LocalDateTime inputDate) {
		super();
		this.price = price;
		this.inputDate = inputDate;
	}

	public Price() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the inputDate
	 */
	public LocalDateTime getInputDate() {
		return inputDate;
	}

	/**
	 * @param inputDate the inputDate to set
	 */
	public void setInputDate(LocalDateTime inputDate) {
		this.inputDate = inputDate;
	}
	

}
