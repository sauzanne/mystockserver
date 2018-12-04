package fr.mystocks.mystockserver.data.finance.measurecalculation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

@Entity
@Table(name="mystocks.measure_calculation")
public class MeasureCalculation implements Serializable {
	private static final long serialVersionUID = 3464037981150236245L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "code")
	private String code;
	
	@Column(name="review_id")
	private Review review;
	
	@Column(name="stock_ticker_id")
	private StockTicker stockTicker;
	
	@Column(name="measure_id")
	private Measure measure;
	
	@Column(name="value")
	private BigDecimal value;
		
	@Column(name="first_input")
	private LocalDateTime firstInput;
	
	@Column(name="last_modified")
	private LocalDateTime lastModified;

	/**
	 * @param id
	 * @param code
	 * @param review
	 * @param stockTicker
	 * @param measure
	 * @param value
	 * @param firstInput
	 * @param lastModified
	 */
	public MeasureCalculation(int id, String code, Review review, StockTicker stockTicker, Measure measure,
			BigDecimal value, LocalDateTime firstInput, LocalDateTime lastModified) {
		super();
		this.id = id;
		this.code = code;
		this.review = review;
		this.stockTicker = stockTicker;
		this.measure = measure;
		this.value = value;
		this.firstInput = firstInput;
		this.lastModified = lastModified;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the review
	 */
	public Review getReview() {
		return review;
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(Review review) {
		this.review = review;
	}

	/**
	 * @return the stockTicker
	 */
	public StockTicker getStockTicker() {
		return stockTicker;
	}

	/**
	 * @param stockTicker the stockTicker to set
	 */
	public void setStockTicker(StockTicker stockTicker) {
		this.stockTicker = stockTicker;
	}

	/**
	 * @return the measure
	 */
	public Measure getMeasure() {
		return measure;
	}

	/**
	 * @param measure the measure to set
	 */
	public void setMeasure(Measure measure) {
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
	 * @return the firstInput
	 */
	public LocalDateTime getFirstInput() {
		return firstInput;
	}

	/**
	 * @param firstInput the firstInput to set
	 */
	public void setFirstInput(LocalDateTime firstInput) {
		this.firstInput = firstInput;
	}

	/**
	 * @return the lastModified
	 */
	public LocalDateTime getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((firstInput == null) ? 0 : firstInput.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((measure == null) ? 0 : measure.hashCode());
		result = prime * result + ((review == null) ? 0 : review.hashCode());
		result = prime * result + ((stockTicker == null) ? 0 : stockTicker.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		MeasureCalculation other = (MeasureCalculation) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (firstInput == null) {
			if (other.firstInput != null)
				return false;
		} else if (!firstInput.equals(other.firstInput))
			return false;
		if (id != other.id)
			return false;
		if (lastModified == null) {
			if (other.lastModified != null)
				return false;
		} else if (!lastModified.equals(other.lastModified))
			return false;
		if (measure == null) {
			if (other.measure != null)
				return false;
		} else if (!measure.equals(other.measure))
			return false;
		if (review == null) {
			if (other.review != null)
				return false;
		} else if (!review.equals(other.review))
			return false;
		if (stockTicker == null) {
			if (other.stockTicker != null)
				return false;
		} else if (!stockTicker.equals(other.stockTicker))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	
}
