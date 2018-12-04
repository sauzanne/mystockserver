package fr.mystocks.mystockserver.view.model.finance.balancesheets;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.mystocks.mystockserver.data.finance.balancesheets.BalanceSheets;
import fr.mystocks.mystockserver.technic.serializer.LocalDateTimeSerializer;
import fr.mystocks.mystockserver.view.model.finance.assets.AssetsModel;
import fr.mystocks.mystockserver.view.model.finance.equities.EquitiesModel;
import fr.mystocks.mystockserver.view.model.finance.liabilities.LiabilitiesModel;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class BalanceSheetsModel {
	
	private Integer id;

	private Double shareholderEquity;

	private EquitiesModel equities;

	private AssetsModel assets;

	private LiabilitiesModel liabilities;


	@JsonProperty("lastModified")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastModified;

	
	@JsonProperty("firstInput")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime firstInput;

	@JsonCreator
	public BalanceSheetsModel() {
		super();
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
	 * @return the shareholderEquity
	 */
	public Double getShareholderEquity() {
		return shareholderEquity;
	}

	/**
	 * @param shareholderEquity
	 *            the shareholderEquity to set
	 */
	public void setShareholderEquity(Double shareholderEquity) {
		this.shareholderEquity = shareholderEquity;
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
	 * @return the equitiesModel
	 */
	public EquitiesModel getEquitiesModel() {
		return equities;
	}

	/**
	 * @param equitiesModel
	 *            the equitiesModel to set
	 */
	public void setEquitiesModel(EquitiesModel equitiesModel) {
		this.equities = equitiesModel;
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
	 * @return the assetsModel
	 */
	public AssetsModel getAssetsModel() {
		return assets;
	}

	/**
	 * @param assetsModel
	 *            the assetsModel to set
	 */
	public void setAssetsModel(AssetsModel assetsModel) {
		this.assets = assetsModel;
	}

	public void convertFromBalanceSheets(BalanceSheets b) {
		BeanUtils.copyProperties(b, this);

		if (b.getAssets() != null) {
			assets = new AssetsModel();
			assets.convertFromAssets(b.getAssets());
		}

		if (b.getEquities() != null) {
			equities = new EquitiesModel();
			equities.convertFromEquities(b.getEquities());
		}

		if (b.getLiabilities() != null) {
			liabilities = new LiabilitiesModel();
			liabilities.convertFromLiabilities(b.getLiabilities());
		}
	}

}