package fr.mystocks.mystockserver.view.model.finance.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.view.model.finance.stockticker.StockTickerModel;
import fr.mystocks.mystockserver.view.model.finance.stocktype.StockTypeModel;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class StockModel {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("isin")
    private String isin;

    @JsonProperty("name")
    private String name;

    @JsonProperty("stockType")
    private StockTypeModel stockType;

    @JsonProperty("stockTickers")
    private List<StockTickerModel> stockTickers;

    /**
     *
     */
    @JsonCreator
    public StockModel() {
	super();
    }

    /**
     *
     * @param id
     * @param isin
     * @param name
     * @param stockType
     */
    @JsonCreator
    public StockModel(Integer id, String isin, String name, StockTypeModel stockType,
	    List<StockTickerModel> stockTickers) {
	super();
	this.id = id;
	this.isin = isin;
	this.name = name;
	this.stockType = stockType;
	this.stockTickers = stockTickers;
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
     * @author sauzanne @return the isin
     */
    public String getIsin() {
	return isin;
    }

    /**
     * @author sauzanne @param isin the isin to set
     */
    public void setIsin(String isin) {
	this.isin = isin;
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
     * @author sauzanne @return the stockType
     */
    public StockTypeModel getStockType() {
	return stockType;
    }

    /**
     * @author sauzanne @param stockType the stockType to set
     */
    public void setStockType(StockTypeModel stockType) {
	this.stockType = stockType;
    }

    /**
     * @author sauzanne @return the stockTickers
     */
    public List<StockTickerModel> getStockTickers() {
	return stockTickers;
    }

    /**
     * @author sauzanne @param stockTickers the stockTickers to set
     */
    public void setStockTickers(List<StockTickerModel> stockTickers) {
	this.stockTickers = stockTickers;
    }

    public void convertFromStock(Stock s, boolean loadStockTicker) {
	BeanUtils.copyProperties(s, this);
	if (s.getStockType() != null) {
	    setStockType(new StockTypeModel());
	    getStockType().convertFromStockType(s.getStockType());
	}
	if (s.getListStockTicker() != null && loadStockTicker) {
	    setStockTickers(new ArrayList<>());
	    for (StockTicker st : s.getListStockTicker()) {
		StockTickerModel stm = new StockTickerModel();
		stm.convertFromStockTicker(st, false);
		getStockTickers().add(stm);
	    }
	}
    }

}
