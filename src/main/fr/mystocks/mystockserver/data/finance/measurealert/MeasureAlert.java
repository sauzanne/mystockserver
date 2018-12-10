package fr.mystocks.mystockserver.data.finance.measurealert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.mystocks.mystockserver.data.finance.measure.Measure;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.data.security.Account;

@Entity
@Table(name = "mystocks.measure_alert")
public class MeasureAlert implements Serializable {
	private static final long serialVersionUID = 3464037981150236245L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "stock_ticker_id")
	private StockTicker stockTicker;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@ManyToOne
	@JoinColumn(name = "measure_id", nullable = false)
	private Measure measure;

	@ManyToOne
	@JoinColumn(name = "measure_id2_compared", nullable = true)
	private Measure measureCompared;

	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "first_input")
	private LocalDateTime firstInput;

	@Column(name = "binaryOperator")
	private String binaryOperator;

	@Column(name = "triggered")
	private boolean triggered;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((binaryOperator == null) ? 0 : binaryOperator.hashCode());
		result = prime * result + ((firstInput == null) ? 0 : firstInput.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((measure == null) ? 0 : measure.hashCode());
		result = prime * result + ((measureCompared == null) ? 0 : measureCompared.hashCode());
		result = prime * result + ((stockTicker == null) ? 0 : stockTicker.hashCode());
		result = prime * result + (triggered ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		MeasureAlert other = (MeasureAlert) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (binaryOperator == null) {
			if (other.binaryOperator != null)
				return false;
		} else if (!binaryOperator.equals(other.binaryOperator))
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
		if (measureCompared == null) {
			if (other.measureCompared != null)
				return false;
		} else if (!measureCompared.equals(other.measureCompared))
			return false;
		if (stockTicker == null) {
			if (other.stockTicker != null)
				return false;
		} else if (!stockTicker.equals(other.stockTicker))
			return false;
		if (triggered != other.triggered)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
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
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
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
	 * @return the measureCompared
	 */
	public Measure getMeasureCompared() {
		return measureCompared;
	}

	/**
	 * @param measureCompared the measureCompared to set
	 */
	public void setMeasureCompared(Measure measureCompared) {
		this.measureCompared = measureCompared;
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
	 * @return the binaryOperator
	 */
	public String getBinaryOperator() {
		return binaryOperator;
	}

	/**
	 * @param binaryOperator the binaryOperator to set
	 */
	public void setBinaryOperator(String binaryOperator) {
		this.binaryOperator = binaryOperator;
	}

	/**
	 * @return the triggered
	 */
	public boolean isTriggered() {
		return triggered;
	}

	/**
	 * @param triggered the triggered to set
	 */
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
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

	/**
	 * @param id
	 * @param stockTicker
	 * @param account
	 * @param measure
	 * @param measureCompared
	 * @param value
	 * @param firstInput
	 * @param binaryOperator
	 * @param triggered
	 * @param lastModified
	 */
	public MeasureAlert(int id, StockTicker stockTicker, Account account, Measure measure, Measure measureCompared,
			BigDecimal value, LocalDateTime firstInput, String binaryOperator, boolean triggered,
			LocalDateTime lastModified) {
		super();
		this.id = id;
		this.stockTicker = stockTicker;
		this.account = account;
		this.measure = measure;
		this.measureCompared = measureCompared;
		this.value = value;
		this.firstInput = firstInput;
		this.binaryOperator = binaryOperator;
		this.triggered = triggered;
		this.lastModified = lastModified;
	}

}
