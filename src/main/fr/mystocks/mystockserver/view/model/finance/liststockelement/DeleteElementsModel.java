package fr.mystocks.mystockserver.view.model.finance.liststockelement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
//)
public class DeleteElementsModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3841487161880134846L;

	//@JsonProperty("listStockId")
	private String login;
	
	//@JsonProperty("listToDelete")
	private List<Integer> listToDelete = new ArrayList<>();


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Integer> getListToDelete() {
		return listToDelete;
	}

	public void setListToDelete(List<Integer> listToDelete) {
		this.listToDelete = listToDelete;
	}
	
	

}
