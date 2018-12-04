package fr.mystocks.mystockserver.view.model.finance.review;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.technic.serializer.LocalDateSerializer;
import fr.mystocks.mystockserver.technic.serializer.LocalDateTimeSerializer;
import fr.mystocks.mystockserver.view.model.finance.balancesheets.BalanceSheetsModel;
import fr.mystocks.mystockserver.view.model.finance.currency.CurrencyModel;
import fr.mystocks.mystockserver.view.model.finance.operations.OperationsModel;
import fr.mystocks.mystockserver.view.model.finance.stock.StockModel;
import fr.mystocks.mystockserver.view.model.security.AccountModel;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class ReviewModel {

	private Integer id;

	private StockModel stock;

	private String period;

	private Integer startYear;

	private Integer endYear;

	private OperationsModel operations;

	private BalanceSheetsModel balanceSheets;

	private AccountModel account;
	
	private BigInteger nbSharesEndPeriod;
	
	private Double freeFloat;
	
	private CurrencyModel currency;
	
	@JsonProperty("startDate")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate startDate;
	
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
	public ReviewModel() {
		super();
	}

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
	 * @return the stock
	 */
	public StockModel getStock() {
		return stock;
	}

	/**
	 * @param stock
	 *            the stock to set
	 */
	public void setStock(StockModel stock) {
		this.stock = stock;
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            the period to set
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
	 * @param startYear
	 *            the startYear to set
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
	 * @param endYear
	 *            the endYear to set
	 */
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	/**
	 * @return the operations
	 */
	public OperationsModel getOperations() {
		return operations;
	}

	/**
	 * @param operations
	 *            the operations to set
	 */
	public void setOperations(OperationsModel operations) {
		this.operations = operations;
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the balanceSheets
	 */
	public BalanceSheetsModel getBalanceSheets() {
		return balanceSheets;
	}

	/**
	 * @param balanceSheets
	 *            the balanceSheets to set
	 */
	public void setBalanceSheets(BalanceSheetsModel balanceSheets) {
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
	public AccountModel getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(AccountModel account) {
		this.account = account;
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

	/**
	 * @return the currency
	 */
	public CurrencyModel getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(CurrencyModel currency) {
		this.currency = currency;
	}

	public void convertFromReview(Review r) {
		BeanUtils.copyProperties(r, this);
		if (r.getOperations() != null) {
			operations = new OperationsModel();
			operations.convertFromOperations(r.getOperations());
		}
		if (r.getStock() != null) {
			stock = new StockModel();
			stock.convertFromStock(r.getStock(), false);
		}
		if (r.getBalanceSheets() != null) {
			balanceSheets = new BalanceSheetsModel();
			balanceSheets.convertFromBalanceSheets(r.getBalanceSheets());
		}
		if (r.getAccount() != null) {
			account = new AccountModel();
			account.convertFormAccount(r.getAccount());
		}
		
		if(r.getCurrency()!=null) {
			currency = new CurrencyModel();
			currency.convertFromCurrency(r.getCurrency());
		}
	}

}
