package fr.mystocks.mystockserver.view.model.finance.stocktype;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.mystocks.mystockserver.data.finance.stocktype.StockType;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class StockTypeModel {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("code")
    private String code;



    /**
     *
     * @param id
     * @param code
     */
    @JsonCreator
    public StockTypeModel(Integer id, String code) {
	super();
	this.id = id;
	this.code = code;
    }
    /**
    *
    */
   @JsonCreator
   public StockTypeModel() {
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



    public void convertFromStockType(StockType st) {
	BeanUtils.copyProperties(st, this);
    }

}
