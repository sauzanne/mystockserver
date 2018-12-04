package fr.mystocks.mystockserver.view.model.finance.assets;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.mystocks.mystockserver.data.finance.assets.Assets;
import fr.mystocks.mystockserver.technic.serializer.LocalDateTimeSerializer;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class AssetsModel {

	private Integer id;

	private BigDecimal cash;

	private BigDecimal inventories;

	private BigDecimal currentAssets;

	private BigDecimal goodwill;

	private BigDecimal tradeAccounts;

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
	public AssetsModel() {
		super();
	}

	/**
	 * @return the cash
	 */
	public BigDecimal getCash() {
		return cash;
	}

	/**
	 * @param cash
	 *            the cash to set
	 */
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	/**
	 * @return the inventories
	 */
	public BigDecimal getInventories() {
		return inventories;
	}

	/**
	 * @param inventories
	 *            the inventories to set
	 */
	public void setInventories(BigDecimal inventories) {
		this.inventories = inventories;
	}

	/**
	 * @return the currentAssets
	 */
	public BigDecimal getCurrentAssets() {
		return currentAssets;
	}

	/**
	 * @param currentAssets
	 *            the currentAssets to set
	 */
	public void setCurrentAssets(BigDecimal currentAssets) {
		this.currentAssets = currentAssets;
	}

	/**
	 * @return the goodwill
	 */
	public BigDecimal getGoodwill() {
		return goodwill;
	}

	/**
	 * @param goodwill
	 *            the goodwill to set
	 */
	public void setGoodwill(BigDecimal goodwill) {
		this.goodwill = goodwill;
	}

	/**
	 * @return the tradeAccounts
	 */
	public BigDecimal getTradeAccounts() {
		return tradeAccounts;
	}

	/**
	 * @param tradeAccounts
	 *            the tradeAccounts to set
	 */
	public void setTradeAccounts(BigDecimal tradeAccounts) {
		this.tradeAccounts = tradeAccounts;
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

	public void convertFromAssets(Assets a) {
		BeanUtils.copyProperties(a, this);
	}

}