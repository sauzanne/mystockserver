package fr.mystocks.mystockserver.data.finance.valuation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mystocks.valuation")
public class Valuation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2257467672863435263L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "expected_growth_rate")
	private BigDecimal expectedGrowthRate;

	@Column(name = "first_input")
	private LocalDateTime firstInput;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;

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
	 * @return the expectedGrowth
	 */
	public BigDecimal getExpectedGrowthRate() {
		return expectedGrowthRate;
	}

	/**
	 * @param expectedGrowth the expectedGrowth to set
	 */
	public void setExpectedGrowthRate(BigDecimal expectedGrowth) {
		this.expectedGrowthRate = expectedGrowth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(expectedGrowthRate, firstInput, id, lastModified);
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
		Valuation other = (Valuation) obj;
		return Objects.equals(expectedGrowthRate, other.expectedGrowthRate) && Objects.equals(firstInput, other.firstInput)
				&& id == other.id && Objects.equals(lastModified, other.lastModified);
	}

	public LocalDateTime getFirstInput() {
		return firstInput;
	}

	public void setFirstInput(LocalDateTime firstInput) {
		this.firstInput = firstInput;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public Valuation() {
		super();
	}

	/**
	 * @param id
	 */
	public Valuation(int id) {
		super();
		this.id = id;
	}

}
