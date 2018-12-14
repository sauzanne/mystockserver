package fr.mystocks.mystockserver.data.finance.portfoliohistory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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

import fr.mystocks.mystockserver.data.finance.portfolio.Portfolio;
import fr.mystocks.mystockserver.data.finance.portfolio.reason.Reason;
import fr.mystocks.mystockserver.data.finance.portfolio.typeoperation.TypeOperation;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.security.Account;

@Entity
@Table(name = "mystocks.portfolio_history")
public class PortfolioHistory implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1208285925492944671L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "portfolio_id", nullable = false)
	private Portfolio portfolio;

	@Column(name = "nb")
	private Integer nb;


	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "fx_spot")
	private BigDecimal fxSpot;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_operation_id", nullable = false)
	private TypeOperation typeOperation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_id", nullable = true)
	private Reason reason;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stock_id", nullable = true)
	private Stock stock;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "portfolio_history_id_father", nullable = true)
	private PortfolioHistory portfolioHistory;

	@Column(name = "date_operation")
	private LocalDate dateOperation;
	
	@Column(name = "description")
	private String description;


	
	@Column(name = "first_input")
	private LocalDateTime firstInput;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;

	public PortfolioHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PortfolioHistory(Integer id, Portfolio portfolio, Integer nb, BigDecimal value, BigDecimal fxSpot,
			TypeOperation typeOperation, Reason reason, Account account, Stock stock, PortfolioHistory portfolioHistory,
			LocalDate dateOperation, String description, LocalDateTime firstInput, LocalDateTime lastModified) {
		super();
		this.id = id;
		this.portfolio = portfolio;
		this.nb = nb;
		this.value = value;
		this.fxSpot = fxSpot;
		this.typeOperation = typeOperation;
		this.reason = reason;
		this.account = account;
		this.stock = stock;
		this.portfolioHistory = portfolioHistory;
		this.dateOperation = dateOperation;
		this.description = description;
		this.firstInput = firstInput;
		this.lastModified = lastModified;
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
	 * @return the portfolio
	 */
	public Portfolio getPortfolio() {
		return portfolio;
	}

	/**
	 * @param portfolio the portfolio to set
	 */
	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * @return the nb
	 */
	public Integer getNb() {
		return nb;
	}

	/**
	 * @param nb the nb to set
	 */
	public void setNb(Integer nb) {
		this.nb = nb;
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
	 * @return the fxSpot
	 */
	public BigDecimal getFxSpot() {
		return fxSpot;
	}

	/**
	 * @param fxSpot the fxSpot to set
	 */
	public void setFxSpot(BigDecimal fxSpot) {
		this.fxSpot = fxSpot;
	}

	/**
	 * @return the typeOperation
	 */
	public TypeOperation getTypeOperation() {
		return typeOperation;
	}

	/**
	 * @param typeOperation the typeOperation to set
	 */
	public void setTypeOperation(TypeOperation typeOperation) {
		this.typeOperation = typeOperation;
	}

	/**
	 * @return the reason
	 */
	public Reason getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(Reason reason) {
		this.reason = reason;
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
	 * @return the portfolioHistory
	 */
	public PortfolioHistory getPortfolioHistory() {
		return portfolioHistory;
	}

	/**
	 * @param portfolioHistory the portfolioHistory to set
	 */
	public void setPortfolioHistory(PortfolioHistory portfolioHistory) {
		this.portfolioHistory = portfolioHistory;
	}

	/**
	 * @return the dateOperation
	 */
	public LocalDate getDateOperation() {
		return dateOperation;
	}

	/**
	 * @param dateOperation the dateOperation to set
	 */
	public void setDateOperation(LocalDate dateOperation) {
		this.dateOperation = dateOperation;
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
		return Objects.hash(account, dateOperation, description, firstInput, fxSpot, id, lastModified, nb, value);
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
		PortfolioHistory other = (PortfolioHistory) obj;
		return Objects.equals(account, other.account) && Objects.equals(dateOperation, other.dateOperation)
				&& Objects.equals(description, other.description) && Objects.equals(firstInput, other.firstInput)
				&& Objects.equals(fxSpot, other.fxSpot) && Objects.equals(id, other.id)
				&& Objects.equals(lastModified, other.lastModified) && Objects.equals(nb, other.nb)
				&& Objects.equals(value, other.value);
	}
	
	
	
}
