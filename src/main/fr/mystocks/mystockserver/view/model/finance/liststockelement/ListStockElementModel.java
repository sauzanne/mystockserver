package fr.mystocks.mystockserver.view.model.finance.liststockelement;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.mystocks.mystockserver.data.finance.liststockelement.ListStockElement;
import fr.mystocks.mystockserver.view.model.finance.stock.StockModel;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class ListStockElementModel {

	@JsonProperty
	private Integer id;

	@JsonProperty
	private StockModel stock;
	
	@JsonProperty
	private Integer listStockId;

	@JsonCreator
	public ListStockElementModel() {
		super();
	}

	@JsonCreator
	public ListStockElementModel(Integer id, StockModel stock) {
		super();
		this.id = id;
		this.stock = stock;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StockModel getStock() {
		return stock;
	}

	public void setStock(StockModel stock) {
		this.stock = stock;
	}

	public Integer getListStockId() {
		return listStockId;
	}

	public void setListStockId(Integer listStockId) {
		this.listStockId = listStockId;
	}

	public void convertFromListStockElement(ListStockElement lse) {
		BeanUtils.copyProperties(lse, this);
		if (lse.getStock() != null) {
			this.stock = new StockModel();
			this.stock.convertFromStock(lse.getStock(), true);
		}
	}

}
