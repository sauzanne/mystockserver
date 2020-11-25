package fr.mystocks.mystockserver.view.model.finance.liabilities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.mystocks.mystockserver.data.finance.liabilities.Liabilities;
import fr.mystocks.mystockserver.technic.serializer.LocalDateTimeSerializer;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class LiabilitiesModel {

	private Integer id;

	private BigDecimal currentLiabilities;

	private BigDecimal shortTermBorrowings;

	private BigDecimal longTermBorrowings;
	
	private BigDecimal capitalLeases;

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
	public LiabilitiesModel() {
		super();
	}


	/**
	 * @return the currentLiabilities
	 */
	public BigDecimal getCurrentLiabilities() {
		return currentLiabilities;
	}


	/**
	 * @param currentLiabilities the currentLiabilities to set
	 */
	public void setCurrentLiabilities(BigDecimal currentLiabilities) {
		this.currentLiabilities = currentLiabilities;
	}


	/**
	 * @return the shortTermBorrowings
	 */
	public BigDecimal getShortTermBorrowings() {
		return shortTermBorrowings;
	}


	/**
	 * @param shortTermBorrowings the shortTermBorrowings to set
	 */
	public void setShortTermBorrowings(BigDecimal shortTermBorrowings) {
		this.shortTermBorrowings = shortTermBorrowings;
	}


	/**
	 * @return the longTermBorrowings
	 */
	public BigDecimal getLongTermBorrowings() {
		return longTermBorrowings;
	}


	/**
	 * @param longTermBorrowings the longTermBorrowings to set
	 */
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

	public void convertFromLiabilities(Liabilities l) {
		BeanUtils.copyProperties(l, this);

	}

}