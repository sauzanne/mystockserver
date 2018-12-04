package fr.mystocks.mystockserver.data.finance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mystocks.equities")
public class Equities implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -862203720509359542L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "shareholder_equity")
	private BigDecimal shareholderEquity;
	
	@Column(name = "non_controlling_interests")
	private BigDecimal nonControllingInterests;

	
	@Column(name = "first_input")
	private LocalDateTime firstInput;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;
	
	

	/**
	 * 
	 */
	public Equities() {
		super();
	}

	/**
	 * @param id
	 */
	public Equities(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public BigDecimal getShareholderEquity() {
		return shareholderEquity;
	}

	public void setShareholderEquity(BigDecimal shareholderEquity) {
		this.shareholderEquity = shareholderEquity;
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

	/**
	 * @return the nonControllingInterests
	 */
	public BigDecimal getNonControllingInterests() {
		return nonControllingInterests;
	}

	/**
	 * @param nonControllingInterests the nonControllingInterests to set
	 */
	public void setNonControllingInterests(BigDecimal nonControllingInterests) {
		this.nonControllingInterests = nonControllingInterests;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstInput == null) ? 0 : firstInput.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((nonControllingInterests == null) ? 0 : nonControllingInterests.hashCode());
		result = prime * result + ((shareholderEquity == null) ? 0 : shareholderEquity.hashCode());
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
		Equities other = (Equities) obj;
		if (firstInput == null) {
			if (other.firstInput != null)
				return false;
		} else if (!firstInput.equals(other.firstInput))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastModified == null) {
			if (other.lastModified != null)
				return false;
		} else if (!lastModified.equals(other.lastModified))
			return false;
		if (nonControllingInterests == null) {
			if (other.nonControllingInterests != null)
				return false;
		} else if (!nonControllingInterests.equals(other.nonControllingInterests))
			return false;
		if (shareholderEquity == null) {
			if (other.shareholderEquity != null)
				return false;
		} else if (!shareholderEquity.equals(other.shareholderEquity))
			return false;
		return true;
	}

	

}
