package fr.mystocks.mystockserver.data.finance.review;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.mystocks.mystockserver.data.finance.balancesheets.BalanceSheets;
import fr.mystocks.mystockserver.data.finance.currency.Currency;
import fr.mystocks.mystockserver.data.finance.operations.Operations;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.security.Account;

@Entity
@Table(name = "mystocks.review")
public class Review implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5759094879661972075L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stock;

	@Column(name = "period")
	private String period;

	@Column(name = "start_year")
	private Integer startYear;

	@Column(name = "end_year")
	private Integer endYear;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "publication_date")
	private LocalDate publicationDate;


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operations_id", nullable = true)
	private Operations operations;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "balance_sheets_id", nullable = true)
	private BalanceSheets balanceSheets;
	

	@Column(name = "nb_shares_end_period")
	private BigInteger nbSharesEndPeriod;
	
	@Column(name = "free_float")
	private Double freeFloat;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "currency_id", nullable = true)
	private Currency currency;

	@Column(name = "first_input")
	private LocalDateTime firstInput;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param stock
	 * @param period
	 * @param startYear
	 * @param endYear
	 * @param operations
	 * @param balanceSheets
	 * @param nbSharesEndPeriod
	 * @param account
	 * @param currency
	 * @param firstInput
	 * @param lastModified
	 */
	public Review(int id, Stock stock, String period, Integer startYear, Integer endYear, Operations operations,
			BalanceSheets balanceSheets, BigInteger nbSharesEndPeriod, Account account, Currency currency,
			LocalDateTime firstInput, LocalDateTime lastModified) {
		super();
		this.id = id;
		this.stock = stock;
		this.period = period;
		this.startYear = startYear;
		this.endYear = endYear;
		this.operations = operations;
		this.balanceSheets = balanceSheets;
		this.nbSharesEndPeriod = nbSharesEndPeriod;
		this.account = account;
		this.currency = currency;
		this.firstInput = firstInput;
		this.lastModified = lastModified;
	}

	/**
	 * 
	 */
	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
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
	 * @return the startYear
	 */
	public Integer getStartYear() {
		return startYear;
	}

	/**
	 * @param startYear the startYear to set
	 */
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	/**
	 * @return the endYear
	 */
	public Integer getEndYear() {
		return endYear;
	}

	/**
	 * @param endYear the endYear to set
	 */
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	/**
	 * @return the operations
	 */
	public Operations getOperations() {
		return operations;
	}

	/**
	 * @param operations the operations to set
	 */
	public void setOperations(Operations operations) {
		this.operations = operations;
	}

	/**
	 * @return the balanceSheets
	 */
	public BalanceSheets getBalanceSheets() {
		return balanceSheets;
	}

	/**
	 * @param balanceSheets the balanceSheets to set
	 */
	public void setBalanceSheets(BalanceSheets balanceSheets) {
		this.balanceSheets = balanceSheets;
	}

	/**
	 * @return the nbSharesEndPeriod
	 */
	public BigInteger getNbSharesEndPeriod() {
		return nbSharesEndPeriod;
	}

	/**
	 * @param nbSharesEndPeriod the nbSharesEndPeriod to set
	 */
	public void setNbSharesEndPeriod(BigInteger nbSharesEndPeriod) {
		this.nbSharesEndPeriod = nbSharesEndPeriod;
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
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * @return the firstInput
	 */
	public LocalDateTime getFirstInput() {
		return firstInput;
	}

	/**
	 * @param firstInput
	 *            the firstInput to set
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
	 * @param lastModified
	 *            the lastModified to set
	 */
	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the publicationDate
	 */
	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	/**
	 * @param publicationDate the publicationDate to set
	 */
	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}

	/**
	 * @return the freeFloat
	 */
	public Double getFreeFloat() {
		return freeFloat;
	}

	/**
	 * @param freeFloat the freeFloat to set
	 */
	public void setFreeFloat(Double freeFloat) {
		this.freeFloat = freeFloat;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endYear == null) ? 0 : endYear.hashCode());
		result = prime * result + ((firstInput == null) ? 0 : firstInput.hashCode());
		result = prime * result + id;
		result = prime * result + ((nbSharesEndPeriod == null) ? 0 : nbSharesEndPeriod.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((startYear == null) ? 0 : startYear.hashCode());
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
		Review other = (Review) obj;
		if (endYear == null) {
			if (other.endYear != null)
				return false;
		} else if (!endYear.equals(other.endYear))
			return false;
		if (firstInput == null) {
			if (other.firstInput != null)
				return false;
		} else if (!firstInput.equals(other.firstInput))
			return false;
		if (id != other.id)
			return false;
		if (nbSharesEndPeriod == null) {
			if (other.nbSharesEndPeriod != null)
				return false;
		} else if (!nbSharesEndPeriod.equals(other.nbSharesEndPeriod))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		if (startYear == null) {
			if (other.startYear != null)
				return false;
		} else if (!startYear.equals(other.startYear))
			return false;
		return true;
	}

}
