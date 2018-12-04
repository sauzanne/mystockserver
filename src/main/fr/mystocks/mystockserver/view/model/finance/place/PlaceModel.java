package fr.mystocks.mystockserver.view.model.finance.place;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.mystocks.mystockserver.data.finance.place.Place;
import fr.mystocks.mystockserver.view.model.finance.currency.CurrencyModel;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class PlaceModel {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("code")
	private String code;

	@JsonProperty("placeName")
	private String placeName;

	@JsonProperty("currency")
	private CurrencyModel currency;

	@JsonProperty("countryName")
	private String countryName;

	/**
	 *
	 * @param id
	 * @param code
	 */
	@JsonCreator
	public PlaceModel(Integer id, String code, String placeName, CurrencyModel currency, String countryName) {
		super();
		this.id = id;
		this.code = code;
		this.placeName = placeName;
		this.currency = currency;
		this.countryName = countryName;
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
	 * @author sauzanne @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @author sauzanne @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @author sauzanne @return the placeName
	 */
	public String getPlaceName() {
		return placeName;
	}

	/**
	 * @author sauzanne @param placeName the placeName to set
	 */
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	/**
	 * @author sauzanne @return the currency
	 */
	public CurrencyModel getCurrency() {
		return currency;
	}

	/**
	 * @author sauzanne @param currency the currency to set
	 */
	public void setCurrency(CurrencyModel currency) {
		this.currency = currency;
	}

	/**
	 * @author sauzanne @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @author sauzanne @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 *
	 */
	@JsonCreator
	public PlaceModel() {
		super();
	}

	public void convertFromPlace(Place p) {
		BeanUtils.copyProperties(p, this);

		if (p.getCurrency() != null) {
			setCurrency(new CurrencyModel());
			getCurrency().convertFromCurrency(p.getCurrency());
		}
	}

}
