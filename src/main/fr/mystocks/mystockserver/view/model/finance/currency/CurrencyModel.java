package fr.mystocks.mystockserver.view.model.finance.currency;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.mystocks.mystockserver.data.finance.currency.Currency;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class CurrencyModel {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("alpha3")
    private String alpha3;

    /**
     *
     * @param id
     * @param name
     * @param alpha3
     */
    @JsonCreator
    public CurrencyModel(Integer id, String name, String alpha3) {
	super();
	this.id = id;
	this.name = name;
	this.alpha3 = alpha3;
    }

    /**
     *
     */
    @JsonCreator
    public CurrencyModel() {
	super();
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
     * @author sauzanne @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @author sauzanne @param name the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @author sauzanne @return the alpha3
     */
    public String getAlpha3() {
	return alpha3;
    }

    /**
     * @author sauzanne @param alpha3 the alpha3 to set
     */
    public void setAlpha3(String alpha3) {
	this.alpha3 = alpha3;
    }

    public void convertFromCurrency(Currency c) {
	BeanUtils.copyProperties(c, this);
    }

}
