package fr.mystocks.mystockserver.data.finance.stockticker;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.mystocks.mystockserver.data.finance.place.Place;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.security.User;

@Entity
@Table(name="mystocks.stock_ticker")
public class StockTicker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6698090379322377736L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "code",length=5)
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stock;
	
	@ManyToOne
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;

	@Column(name = "main_place")
	private Boolean mainPlace;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "server_user_id", nullable = false)
	private User user;


	@Column(name = "first_input")
	private LocalDateTime firstInput;


	@Column(name = "last_modified")
	private LocalDateTime lastModified;


	/**
	 *
	 */
	public StockTicker() {
	    super();
	}

	/**
	 *
	 * @param code
	 * @param place
	 */
	public StockTicker(String code, Place place) {
	    super();
	    this.code = code;
	    this.place = place;
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
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}

	/**
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(Place place) {
		this.place = place;
	}

	/**
	 * @return the mainPlace
	 */
	public Boolean getMainPlace() {
		return mainPlace;
	}

	/**
	 * @param mainPlace the mainPlace to set
	 */
	public void setMainPlace(Boolean mainPlace) {
		this.mainPlace = mainPlace;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((code == null) ? 0 : code.hashCode());
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    result = prime * result + ((mainPlace == null) ? 0 : mainPlace.hashCode());
	    return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (getClass() != obj.getClass())
		return false;
	    StockTicker other = (StockTicker) obj;
	    if (code == null) {
		if (other.code != null)
		    return false;
	    } else if (!code.equals(other.code))
		return false;
	    if (id == null) {
		if (other.id != null)
		    return false;
	    } else if (!id.equals(other.id))
		return false;
	    if (mainPlace == null) {
		if (other.mainPlace != null)
		    return false;
	    } else if (!mainPlace.equals(other.mainPlace))
		return false;
	    return true;
	}

	/**
	 * @author sauzanne @return the user
	 */
	public User getUser() {
	    return user;
	}

	/**
	 * @author sauzanne @param user the user to set
	 */
	public void setUser(User user) {
	    this.user = user;
	}

	/**
	 * @author sauzanne @return the firstInput
	 */
	public LocalDateTime getFirstInput() {
	    return firstInput;
	}

	/**
	 * @author sauzanne @param firstInput the firstInput to set
	 */
	public void setFirstInput(LocalDateTime firstInput) {
	    this.firstInput = firstInput;
	}

	/**
	 * @author sauzanne @return the lastModified
	 */
	public LocalDateTime getLastModified() {
	    return lastModified;
	}

	/**
	 * @author sauzanne @param lastModified the lastModified to set
	 */
	public void setLastModified(LocalDateTime lastModified) {
	    this.lastModified = lastModified;
	}

}
