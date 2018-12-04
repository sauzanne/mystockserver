package fr.mystocks.mystockserver.view.model.finance.stockticker;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.view.model.finance.place.PlaceModel;
import fr.mystocks.mystockserver.view.model.finance.stock.StockModel;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class StockTickerModel {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("place")
    private PlaceModel place;

    @JsonProperty("stock")
    private StockModel stock;
    
    @JsonProperty("mainPlace")
    private Boolean mainPlace;

    /**
     *
     * @param id
     * @param code
     * @param place
     * @param stock
     */
    @JsonCreator
    public StockTickerModel(Integer id, String code, PlaceModel place, StockModel stock, Boolean mainPlace) {
	super();
	this.id = id;
	this.code = code;
	this.place = place;
	this.stock = stock;
	this.mainPlace = mainPlace;
    }

    /**
     *
     */
    @JsonCreator
    public StockTickerModel() {
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
     * @author sauzanne @return the place
     */
    public PlaceModel getPlace() {
	return place;
    }

    /**
     * @author sauzanne @param place the place to set
     */
    public void setPlace(PlaceModel place) {
	this.place = place;
    }

    /**
     * @author sauzanne @return the stock
     */
    public StockModel getStock() {
	return stock;
    }

    /**
     * @author sauzanne @param stock the stock to set
     */
    public void setStock(StockModel stock) {
	this.stock = stock;
    }
    
    

    /**
     * @author sauzanne @return the mainPlace
     */
    public Boolean getMainPlace() {
        return mainPlace;
    }

    /**
     * @author sauzanne @param mainPlace the mainPlace to set
     */
    public void setMainPlace(Boolean mainPlace) {
        this.mainPlace = mainPlace;
    }

    /**
     * @author sauzanne
     * Convertit un objet métier en model Json
     * @param st un StockTicker
     * @param loadStock défini si on doit charger le titre associé, important pour éviter des boucles infinies
     */
    public void convertFromStockTicker(StockTicker st, boolean loadStock) {
	BeanUtils.copyProperties(st, this);
	if (st.getPlace() != null) {
	    setPlace(new PlaceModel());
	    getPlace().convertFromPlace(st.getPlace());
	}
	if (st.getStock() != null && loadStock) {
	    setStock(new StockModel());
	    getStock().convertFromStock(st.getStock(), false);
	}
    }

}
