package fr.mystocks.mystockserver.view.model.finance.liststock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.mystocks.mystockserver.data.finance.liststock.ListStock;
import fr.mystocks.mystockserver.data.finance.liststockelement.ListStockElement;
import fr.mystocks.mystockserver.view.model.finance.liststockelement.ListStockElementModel;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class ListStockModel {

	@JsonProperty
	private Integer id;

	@JsonProperty
	private String name;

	@JsonProperty
	private Integer accountId;

	@JsonProperty
	private List<ListStockElementModel> stockElements;

	@JsonCreator
	public ListStockModel() {
		super();
	}

	@JsonCreator
	public ListStockModel(Integer id, String name, Integer accountId) {
		super();
		this.id = id;
		this.name = name;
		this.accountId = accountId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public void convertFromListStock(ListStock ls) {
		BeanUtils.copyProperties(ls, this);
		this.accountId = ls.getAccount().getId();

		if (ls.getListStockElement() != null) {
			this.stockElements = new ArrayList<>();
			for (ListStockElement lse : ls.getListStockElement()) {
				ListStockElementModel lsem = new ListStockElementModel();
				lsem.convertFromListStockElement(lse);
				lsem.setListStockId(this.id);
				this.stockElements.add(lsem);

			}
		}
	}

}
