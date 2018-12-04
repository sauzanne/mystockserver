package fr.mystocks.mystockserver.service.finance.performance;

import java.math.BigDecimal;

public class StockPerformance {
	private String year;

	private String period;

	private String measure;

	private BigDecimal value;

	private String errors;
	
	

	/**
	 * 
	 */
	public StockPerformance() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param year
	 * @param period
	 * @param measure
	 * @param value
	 * @param errors
	 */
	public StockPerformance(String year, String period, String measure, BigDecimal value, String errors) {
		super();
		this.year = year;
		this.period = period;
		this.measure = measure;
		this.value = value;
		this.errors = errors;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * @return the measure
	 */
	public String getMeasure() {
		return measure;
	}

	/**
	 * @param measure the measure to set
	 */
	public void setMeasure(String measure) {
		this.measure = measure;
	}

	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * @return the errors
	 */
	public String getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(String errors) {
		this.errors = errors;
	}

}
