package fr.mystocks.mystockserver.data.finance.portfolio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.mystocks.mystockserver.data.finance.currency.Currency;
import fr.mystocks.mystockserver.data.security.Account;

@Entity
@Table(name = "mystocks.portfolio")
public class Portfolio implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1208285925492944671L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;


	@Column(name = "name")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id", nullable = false)
	private Currency currency;


	@Column(name = "default_fees")
	private BigDecimal defaultFees;

	@Column(name = "percent_fees")
	private BigDecimal percentFees;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	
	@Column(name = "first_input")
	private LocalDateTime firstInput;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;

	public Portfolio(Integer id, String name, Currency currency, BigDecimal defaultFees, BigDecimal percentFees,
			String description, Account account, LocalDateTime firstInput, LocalDateTime lastModified) {
		super();
		this.id = id;
		this.name = name;
		this.currency = currency;
		this.defaultFees = defaultFees;
		this.percentFees = percentFees;
		this.description = description;
		this.account = account;
		this.firstInput = firstInput;
		this.lastModified = lastModified;
	}

	public Portfolio() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the defaultFees
	 */
	public BigDecimal getDefaultFees() {
		return defaultFees;
	}

	/**
	 * @param defaultFees the defaultFees to set
	 */
	public void setDefaultFees(BigDecimal defaultFees) {
		this.defaultFees = defaultFees;
	}

	/**
	 * @return the percentFees
	 */
	public BigDecimal getPercentFees() {
		return percentFees;
	}

	/**
	 * @param percentFees the percentFees to set
	 */
	public void setPercentFees(BigDecimal percentFees) {
		this.percentFees = percentFees;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
		return Objects.hash(account, currency, defaultFees, description, firstInput, id, lastModified, name,
				percentFees);
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
		Portfolio other = (Portfolio) obj;
		return Objects.equals(account, other.account) && Objects.equals(currency, other.currency)
				&& Objects.equals(defaultFees, other.defaultFees) && Objects.equals(description, other.description)
				&& Objects.equals(firstInput, other.firstInput) && Objects.equals(id, other.id)
				&& Objects.equals(lastModified, other.lastModified) && Objects.equals(name, other.name)
				&& Objects.equals(percentFees, other.percentFees);
	}

}
