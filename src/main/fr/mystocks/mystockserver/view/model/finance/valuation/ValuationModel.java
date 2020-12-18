package fr.mystocks.mystockserver.view.model.finance.valuation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.mystocks.mystockserver.data.finance.valuation.Valuation;
import fr.mystocks.mystockserver.technic.serializer.LocalDateTimeSerializer;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class ValuationModel {

	private Integer id;

	private BigDecimal expectedGrowthRate;

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
	public ValuationModel() {
		super();
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
	 * @return the expectedGrowth
	 */
	public BigDecimal getExpectedGrowthRate() {
		return expectedGrowthRate;
	}

	/**
	 * @param expectedGrowth the expectedGrowth to set
	 */
	public void setExpectedGrowthRate(BigDecimal expectedGrowth) {
		this.expectedGrowthRate = expectedGrowth;
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

	public void convertFromValuation(Valuation v) {
		BeanUtils.copyProperties(v, this);

	}

}