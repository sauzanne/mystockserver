package fr.mystocks.mystockserver.data.finance.operations;

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
@Table(name="mystocks.operations")
public class Operations implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8119824527002204450L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "revenues")
	private BigDecimal revenues;
	
	@Column(name = "ebit")
	private BigDecimal ebit;

	@Column(name = "current_ebit")
	private BigDecimal currentEbit;
	
	@Column(name = "ebitda")
	private BigDecimal ebitda;
	
	@Column(name = "cost_of_revenues")
	private BigDecimal costOfRevenues;

	@Column(name = "financial_expenses")
	private BigDecimal financialExpenses;
	
	@Column(name = "shareowners_earnings")
	private BigDecimal shareownersEarnings;
	
	@Column(name = "adjusted_earnings")
	private BigDecimal adjustedEarnings;
	
	@Column(name = "operational_cash_flow")
	private BigDecimal operationalCashFlow;

	@Column(name = "free_cash_flow")
	private BigDecimal freeCashFlow;

	@Column(name = "exceptional_items")
	private BigDecimal exceptionalItems;
	

	@Column(name = "first_input")
	private LocalDateTime firstInput;
	
	@Column(name = "last_modified")
	private LocalDateTime lastModified;
	
	

	/**
	 * 
	 */
	public Operations() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 */
	public Operations(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * @author sauzanne @return the id
	 */
	public Integer getId() {
	    return id;
	}

	/**
	 * @author sauzanne @param id the id to set
	 */
	public void setId(Integer id) {
	    this.id = id;
	}

	/**
	 * @author sauzanne @return the revenues
	 */
	public BigDecimal getRevenues() {
	    return revenues;
	}

	/**
	 * @author sauzanne @param revenues the revenues to set
	 */
	public void setRevenues(BigDecimal revenues) {
	    this.revenues = revenues;
	}

	/**
	 * @author sauzanne @return the ebit
	 */
	public BigDecimal getEbit() {
	    return ebit;
	}

	/**
	 * @author sauzanne @param ebit the ebit to set
	 */
	public void setEbit(BigDecimal ebit) {
	    this.ebit = ebit;
	}

	/**
	 * @author sauzanne @return the currentEbit
	 */
	public BigDecimal getCurrentEbit() {
	    return currentEbit;
	}

	/**
	 * @author sauzanne @param currentEbit the currentEbit to set
	 */
	public void setCurrentEbit(BigDecimal currentEbit) {
	    this.currentEbit = currentEbit;
	}

	/**
	 * @author sauzanne @return the ebitda
	 */
	public BigDecimal getEbitda() {
	    return ebitda;
	}

	/**
	 * @author sauzanne @param ebitda the ebitda to set
	 */
	public void setEbitda(BigDecimal ebitda) {
	    this.ebitda = ebitda;
	}

	/**
	 * @author sauzanne @return the costOfRevenues
	 */
	public BigDecimal getCostOfRevenues() {
	    return costOfRevenues;
	}

	/**
	 * @author sauzanne @param costOfRevenues the costOfRevenues to set
	 */
	public void setCostOfRevenues(BigDecimal costOfRevenues) {
	    this.costOfRevenues = costOfRevenues;
	}

	/**
	 * @author sauzanne @return the financialExpenses
	 */
	public BigDecimal getFinancialExpenses() {
	    return financialExpenses;
	}

	/**
	 * @author sauzanne @param financialExpenses the financialExpenses to set
	 */
	public void setFinancialExpenses(BigDecimal financialExpenses) {
	    this.financialExpenses = financialExpenses;
	}

	/**
	 * @author sauzanne @return the shareownersEarnings
	 */
	public BigDecimal getShareownersEarnings() {
	    return shareownersEarnings;
	}

	/**
	 * @author sauzanne @param shareownersEarnings the shareownersEarnings to set
	 */
	public void setShareownersEarnings(BigDecimal shareownersEarnings) {
	    this.shareownersEarnings = shareownersEarnings;
	}
	
	/**
	 * @return the adjustedEarnings
	 */
	public BigDecimal getAdjustedEarnings() {
		return adjustedEarnings;
	}

	/**
	 * @param adjustedEarnings the adjustedEarnings to set
	 */
	public void setAdjustedEarnings(BigDecimal adjustedEarnings) {
		this.adjustedEarnings = adjustedEarnings;
	}


	/**
	 * @author sauzanne @return the operationalCashFlow
	 */
	public BigDecimal getOperationalCashFlow() {
	    return operationalCashFlow;
	}

	/**
	 * @author sauzanne @param operationalCashFlow the operationalCashFlow to set
	 */
	public void setOperationalCashFlow(BigDecimal operationalCashFlow) {
	    this.operationalCashFlow = operationalCashFlow;
	}

	/**
	 * @author sauzanne @return the freeCashFlow
	 */
	public BigDecimal getFreeCashFlow() {
	    return freeCashFlow;
	}

	/**
	 * @author sauzanne @param freeCashFlow the freeCashFlow to set
	 */
	public void setFreeCashFlow(BigDecimal freeCashFlow) {
	    this.freeCashFlow = freeCashFlow;
	}

	/**
	 * @author sauzanne @return the exceptionalItems
	 */
	public BigDecimal getExceptionalItems() {
	    return exceptionalItems;
	}

	/**
	 * @author sauzanne @param exceptionalItems the exceptionalItems to set
	 */
	public void setExceptionalItems(BigDecimal exceptionalItems) {
	    this.exceptionalItems = exceptionalItems;
	}


	/**
	 * @author sauzanne @return the firstInput
	 */
	public LocalDateTime getFirstInput() {
	    return firstInput;
	}

	/**
	 * @author sauzanne @param firstInput the firstInput to set
	 */
	public void setFirstInput(LocalDateTime firstInput) {
	    this.firstInput = firstInput;
	}

	/**
	 * @author sauzanne @return the lastModified
	 */
	public LocalDateTime getLastModified() {
	    return lastModified;
	}

	/**
	 * @author sauzanne @param lastModified the lastModified to set
	 */
	public void setLastModified(LocalDateTime lastModified) {
	    this.lastModified = lastModified;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(adjustedEarnings, costOfRevenues, currentEbit, ebit, ebitda, exceptionalItems,
				financialExpenses, firstInput, freeCashFlow, id, lastModified, operationalCashFlow, revenues,
				shareownersEarnings);
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
		Operations other = (Operations) obj;
		return Objects.equals(adjustedEarnings, other.adjustedEarnings)
				&& Objects.equals(costOfRevenues, other.costOfRevenues)
				&& Objects.equals(currentEbit, other.currentEbit) && Objects.equals(ebit, other.ebit)
				&& Objects.equals(ebitda, other.ebitda) && Objects.equals(exceptionalItems, other.exceptionalItems)
				&& Objects.equals(financialExpenses, other.financialExpenses)
				&& Objects.equals(firstInput, other.firstInput) && Objects.equals(freeCashFlow, other.freeCashFlow)
				&& Objects.equals(id, other.id) && Objects.equals(lastModified, other.lastModified)
				&& Objects.equals(operationalCashFlow, other.operationalCashFlow)
				&& Objects.equals(revenues, other.revenues)
				&& Objects.equals(shareownersEarnings, other.shareownersEarnings);
	}








	
	
	

}
