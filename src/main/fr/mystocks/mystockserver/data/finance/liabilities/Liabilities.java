package fr.mystocks.mystockserver.data.finance.liabilities;

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
    
    @Column(name = "capital_leases")
    private BigDecimal capitalLeases;


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

	/**
	 * @return the capitalLeases
	 */
	public BigDecimal getCapitalLeases() {
		return capitalLeases;
	}

	/**
	 * @param capitalLeases the capitalLeases to set
	 */
	public void setCapitalLeases(BigDecimal capitalLeases) {
		this.capitalLeases = capitalLeases;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(capitalLeases, currentLiabilities, firstInput, id, lastModified, longTermBorrowings,
				shortTermBorrowings);
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
		Liabilities other = (Liabilities) obj;
		return Objects.equals(capitalLeases, other.capitalLeases)
				&& Objects.equals(currentLiabilities, other.currentLiabilities)
				&& Objects.equals(firstInput, other.firstInput) && id == other.id
				&& Objects.equals(lastModified, other.lastModified)
				&& Objects.equals(longTermBorrowings, other.longTermBorrowings)
				&& Objects.equals(shortTermBorrowings, other.shortTermBorrowings);
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


   
}
