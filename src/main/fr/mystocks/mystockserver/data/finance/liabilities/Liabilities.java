package fr.mystocks.mystockserver.data.finance.liabilities;

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
@Table(name = "mystocks.liabilities")
public class Liabilities implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -2257467672863435263L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "current_liabilities")
    private BigDecimal currentLiabilities;

    @Column(name = "short_term_borrowings")
    private BigDecimal shortTermBorrowings;
    
    @Column(name = "long_term_borrowings")
    private BigDecimal longTermBorrowings;

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
     * @param id
     *            the id to set
     */
    public void setId(int id) {
	this.id = id;
    }

	public BigDecimal getCurrentLiabilities() {
		return currentLiabilities;
	}

	public void setCurrentLiabilities(BigDecimal currentLiabilities) {
		this.currentLiabilities = currentLiabilities;
	}

	public BigDecimal getShortTermBorrowings() {
		return shortTermBorrowings;
	}

	public void setShortTermBorrowings(BigDecimal shortTermBorrowings) {
		this.shortTermBorrowings = shortTermBorrowings;
	}

	public BigDecimal getLongTermBorrowings() {
		return longTermBorrowings;
	}

	public void setLongTermBorrowings(BigDecimal longTermBorrowings) {
		this.longTermBorrowings = longTermBorrowings;
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

	public Liabilities(int id, BigDecimal currentLiabilities, BigDecimal shortTermBorrowings,
			BigDecimal longTermBorrowings, LocalDateTime firstInput, LocalDateTime lastModified) {
		super();
		this.id = id;
		this.currentLiabilities = currentLiabilities;
		this.shortTermBorrowings = shortTermBorrowings;
		this.longTermBorrowings = longTermBorrowings;
		this.firstInput = firstInput;
		this.lastModified = lastModified;
	}

	public Liabilities() {
		super();
	}
    
	
	/**
	 * @param id
	 */
	public Liabilities(int id) {
		super();
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentLiabilities == null) ? 0 : currentLiabilities.hashCode());
		result = prime * result + ((firstInput == null) ? 0 : firstInput.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((longTermBorrowings == null) ? 0 : longTermBorrowings.hashCode());
		result = prime * result + ((shortTermBorrowings == null) ? 0 : shortTermBorrowings.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Liabilities other = (Liabilities) obj;
		if (currentLiabilities == null) {
			if (other.currentLiabilities != null)
				return false;
		} else if (!currentLiabilities.equals(other.currentLiabilities))
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
		if (longTermBorrowings == null) {
			if (other.longTermBorrowings != null)
				return false;
		} else if (!longTermBorrowings.equals(other.longTermBorrowings))
			return false;
		if (shortTermBorrowings == null) {
			if (other.shortTermBorrowings != null)
				return false;
		} else if (!shortTermBorrowings.equals(other.shortTermBorrowings))
			return false;
		return true;
	}

   
}
