package fr.mystocks.mystockserver.data.finance.measurecalculation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;

@Entity
@Table(name = "mystocks.measure_calculation")
public class MeasureCalculation implements Serializable {

	private static final long serialVersionUID = 3464037981150236245L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "review_id", nullable = false)
	private Review review;

	@ManyToOne
	@JoinColumn(name = "stock_ticker_id", nullable = false)
	private StockTicker stockTicker;

	@ManyToOne
	@JoinColumn(name = "measure_id", nullable = false)
	private Measure measure;

	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "first_input")
	private LocalDateTime firstInput;

	@Column(name = "last_modified")
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
	public MeasureCalculation(Integer id, Review review, StockTicker stockTicker, Measure measure,
			BigDecimal value, LocalDateTime firstInput, LocalDateTime lastModified) {
		super();
		this.id = id;
		this.review = review;
		this.stockTicker = stockTicker;
		this.measure = measure;
		this.value = value;
		this.firstInput = firstInput;
		this.lastModified = lastModified;
	}

	public MeasureCalculation() {
		super();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
		return Objects.hash(firstInput, id, lastModified, value);
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
		return Objects.equals(firstInput, other.firstInput) && id == other.id
				&& Objects.equals(lastModified, other.lastModified) && Objects.equals(value, other.value);
	}

	
}
