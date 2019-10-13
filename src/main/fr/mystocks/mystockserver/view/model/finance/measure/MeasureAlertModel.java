package fr.mystocks.mystockserver.view.model.finance.measure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class MeasureAlertModel {

	private String stockTicker;
	
	private String stockName;

	private String measure;

	private String comparator;

	private String measureCompared;

	private Double measureCalculation;

	private Double measureCalculationCompared;
	
	private String comment;

	private Boolean triggered;



	public MeasureAlertModel(String stockTicker, String stockName, String measure, String comparator,
			String measureCompared, Double measureCalculation, Double measureCalculationCompared, String comment, Boolean triggered) {
		super();
		this.stockTicker = stockTicker;
		this.stockName = stockName;
		this.measure = measure;
		this.comparator = comparator;
		this.measureCompared = measureCompared;
		this.measureCalculation = measureCalculation;
		this.measureCalculationCompared = measureCalculationCompared;
		this.comment = comment;
		this.triggered = triggered;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	public MeasureAlertModel() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the comparator
	 */
	public String getComparator() {
		return comparator;
	}

	/**
	 * @param comparator the comparator to set
	 */
	public void setComparator(String comparator) {
		this.comparator = comparator;
	}

	/**
	 * @return the measureCompared
	 */
	public String getMeasureCompared() {
		return measureCompared;
	}

	/**
	 * @param measureCompared the measureCompared to set
	 */
	public void setMeasureCompared(String measureCompared) {
		this.measureCompared = measureCompared;
	}

	/**
	 * @return the measureCalculation
	 */
	public Double getMeasureCalculation() {
		return measureCalculation;
	}

	/**
	 * @param measureCalculation the measureCalculation to set
	 */
	public void setMeasureCalculation(Double measureCalculation) {
		this.measureCalculation = measureCalculation;
	}

	/**
	 * @return the measureCalculationCompared
	 */
	public Double getMeasureCalculationCompared() {
		return measureCalculationCompared;
	}

	/**
	 * @param measureCalculationCompared the measureCalculationCompared to set
	 */
	public void setMeasureCalculationCompared(Double measureCalculationCompared) {
		this.measureCalculationCompared = measureCalculationCompared;
	}

	/**
	 * s
	 * 
	 * @return the triggered
	 */
	public Boolean getTriggered() {
		return triggered;
	}

	/**
	 * @param triggered the triggered to set
	 */
	public void setTriggered(Boolean triggered) {
		this.triggered = triggered;
	}

}
