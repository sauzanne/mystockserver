package fr.mystocks.mystockserver.view.model.finance.equities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.mystocks.mystockserver.data.finance.Equities;
import fr.mystocks.mystockserver.technic.serializer.LocalDateTimeSerializer;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class EquitiesModel {

	private Integer id;

	private BigDecimal shareholderEquity;
	
	private BigDecimal nonControllingInterests;

	@JsonProperty("lastModified")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastModified;

	
	@JsonProperty("firstInput")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime firstInput;

	@JsonCreator
	public EquitiesModel() {
		super();
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
	 * @return the shareholderEquity
	 */
	public BigDecimal getShareholderEquity() {
		return shareholderEquity;
	}


	/**
	 * @param shareholderEquity the shareholderEquity to set
	 */
	public void setShareholderEquity(BigDecimal shareholderEquity) {
		this.shareholderEquity = shareholderEquity;
	}


	/**
	 * @return the nonControllingInterests
	 */
	public BigDecimal getNonControllingInterests() {
		return nonControllingInterests;
	}


	/**
	 * @param nonControllingInterests the nonControllingInterests to set
	 */
	public void setNonControllingInterests(BigDecimal nonControllingInterests) {
		this.nonControllingInterests = nonControllingInterests;
	}


	/**
	 * @param lastModified
	 *            the lastModified to set
	 */
	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public void convertFromEquities(Equities e) {
		BeanUtils.copyProperties(e, this);
	}

}