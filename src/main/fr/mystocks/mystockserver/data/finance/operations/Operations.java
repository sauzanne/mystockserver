package fr.mystocks.mystockserver.data.finance.operations;

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
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((costOfRevenues == null) ? 0 : costOfRevenues.hashCode());
	    result = prime * result + ((currentEbit == null) ? 0 : currentEbit.hashCode());
	    result = prime * result + ((ebit == null) ? 0 : ebit.hashCode());
	    result = prime * result + ((ebitda == null) ? 0 : ebitda.hashCode());
	    result = prime * result + ((exceptionalItems == null) ? 0 : exceptionalItems.hashCode());
	    result = prime * result + ((financialExpenses == null) ? 0 : financialExpenses.hashCode());
	    result = prime * result + ((firstInput == null) ? 0 : firstInput.hashCode());
	    result = prime * result + ((freeCashFlow == null) ? 0 : freeCashFlow.hashCode());
	    result = prime * result + id;
	    result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
	    result = prime * result + ((operationalCashFlow == null) ? 0 : operationalCashFlow.hashCode());
	    result = prime * result + ((revenues == null) ? 0 : revenues.hashCode());
	    result = prime * result + ((shareownersEarnings == null) ? 0 : shareownersEarnings.hashCode());
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
	    Operations other = (Operations) obj;
	    if (costOfRevenues == null) {
		if (other.costOfRevenues != null)
		    return false;
	    } else if (!costOfRevenues.equals(other.costOfRevenues))
		return false;
	    if (currentEbit == null) {
		if (other.currentEbit != null)
		    return false;
	    } else if (!currentEbit.equals(other.currentEbit))
		return false;
	    if (ebit == null) {
		if (other.ebit != null)
		    return false;
	    } else if (!ebit.equals(other.ebit))
		return false;
	    if (ebitda == null) {
		if (other.ebitda != null)
		    return false;
	    } else if (!ebitda.equals(other.ebitda))
		return false;
	    if (exceptionalItems == null) {
		if (other.exceptionalItems != null)
		    return false;
	    } else if (!exceptionalItems.equals(other.exceptionalItems))
		return false;
	    if (financialExpenses == null) {
		if (other.financialExpenses != null)
		    return false;
	    } else if (!financialExpenses.equals(other.financialExpenses))
		return false;
	    if (firstInput == null) {
		if (other.firstInput != null)
		    return false;
	    } else if (!firstInput.equals(other.firstInput))
		return false;
	    if (freeCashFlow == null) {
		if (other.freeCashFlow != null)
		    return false;
	    } else if (!freeCashFlow.equals(other.freeCashFlow))
		return false;
	    if (id != other.id)
		return false;
	    if (lastModified == null) {
		if (other.lastModified != null)
		    return false;
	    } else if (!lastModified.equals(other.lastModified))
		return false;
	    if (operationalCashFlow == null) {
		if (other.operationalCashFlow != null)
		    return false;
	    } else if (!operationalCashFlow.equals(other.operationalCashFlow))
		return false;
	    if (revenues == null) {
		if (other.revenues != null)
		    return false;
	    } else if (!revenues.equals(other.revenues))
		return false;
	    if (shareownersEarnings == null) {
		if (other.shareownersEarnings != null)
		    return false;
	    } else if (!shareownersEarnings.equals(other.shareownersEarnings))
		return false;
	    return true;
	}




	
	
	

}
