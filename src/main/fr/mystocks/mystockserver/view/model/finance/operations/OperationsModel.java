package fr.mystocks.mystockserver.view.model.finance.operations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.mystocks.mystockserver.data.finance.operations.Operations;
import fr.mystocks.mystockserver.technic.serializer.LocalDateTimeSerializer;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class OperationsModel {

	private int id;

	private BigDecimal revenues;

	private BigDecimal ebit;

	private BigDecimal currentEbit;

	private BigDecimal ebitda;

	private BigDecimal costOfRevenues;

	private BigDecimal financialExpenses;

	private BigDecimal shareownersEarnings;

	private BigDecimal operationalCashFlow;

	private BigDecimal freeCashFlow;

	private BigDecimal exceptionalItems;
	
	private BigDecimal adjustedEarnings;

	@JsonProperty("lastModified")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastModified;

	
	@JsonProperty("firstInput")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime firstInput;

	/**
	 *
	 */
	@JsonCreator
	public OperationsModel() {
		super();
	}

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

	/**
	 * @return the revenues
	 */
	public BigDecimal getRevenues() {
		return revenues;
	}

	/**
	 * @param revenues
	 *            the revenues to set
	 */
	public void setRevenues(BigDecimal revenues) {
		this.revenues = revenues;
	}

	/**
	 * @return the ebit
	 */
	public BigDecimal getEbit() {
		return ebit;
	}

	/**
	 * @param ebit
	 *            the ebit to set
	 */
	public void setEbit(BigDecimal ebit) {
		this.ebit = ebit;
	}

	/**
	 * @return the currentEbit
	 */
	public BigDecimal getCurrentEbit() {
		return currentEbit;
	}

	/**
	 * @param currentEbit
	 *            the currentEbit to set
	 */
	public void setCurrentEbit(BigDecimal currentEbit) {
		this.currentEbit = currentEbit;
	}

	/**
	 * @return the ebitda
	 */
	public BigDecimal getEbitda() {
		return ebitda;
	}

	/**
	 * @param ebitda
	 *            the ebitda to set
	 */
	public void setEbitda(BigDecimal ebitda) {
		this.ebitda = ebitda;
	}

	/**
	 * @return the costOfRevenues
	 */
	public BigDecimal getCostOfRevenues() {
		return costOfRevenues;
	}

	/**
	 * @param costOfRevenues
	 *            the costOfRevenues to set
	 */
	public void setCostOfRevenues(BigDecimal costOfRevenues) {
		this.costOfRevenues = costOfRevenues;
	}

	/**
	 * @return the financialExpenses
	 */
	public BigDecimal getFinancialExpenses() {
		return financialExpenses;
	}

	/**
	 * @param financialExpenses
	 *            the financialExpenses to set
	 */
	public void setFinancialExpenses(BigDecimal financialExpenses) {
		this.financialExpenses = financialExpenses;
	}

	/**
	 * @return the shareownersEarnings
	 */
	public BigDecimal getShareownersEarnings() {
		return shareownersEarnings;
	}

	/**
	 * @param shareownersEarnings
	 *            the shareownersEarnings to set
	 */
	public void setShareownersEarnings(BigDecimal shareownersEarnings) {
		this.shareownersEarnings = shareownersEarnings;
	}

	/**
	 * @return the operationalCashFlow
	 */
	public BigDecimal getOperationalCashFlow() {
		return operationalCashFlow;
	}

	/**
	 * @param operationalCashFlow
	 *            the operationalCashFlow to set
	 */
	public void setOperationalCashFlow(BigDecimal operationalCashFlow) {
		this.operationalCashFlow = operationalCashFlow;
	}

	/**
	 * @return the freeCashFlow
	 */
	public BigDecimal getFreeCashFlow() {
		return freeCashFlow;
	}

	/**
	 * @param freeCashFlow
	 *            the freeCashFlow to set
	 */
	public void setFreeCashFlow(BigDecimal freeCashFlow) {
		this.freeCashFlow = freeCashFlow;
	}

	/**
	 * @return the exceptionalItems
	 */
	public BigDecimal getExceptionalItems() {
		return exceptionalItems;
	}

	/**
	 * @param exceptionalItems
	 *            the exceptionalItems to set
	 */
	public void setExceptionalItems(BigDecimal exceptionalItems) {
		this.exceptionalItems = exceptionalItems;
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

	public void convertFromOperations(Operations o) {
		BeanUtils.copyProperties(o, this);
	}

}
