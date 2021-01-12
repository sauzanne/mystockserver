package fr.mystocks.mystockserver.data.finance.stock;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.data.finance.stocktype.StockType;
import fr.mystocks.mystockserver.data.security.User;

@Entity
@Table(name = "mystocks.stock")
public class Stock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5759094879661972075L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "isin")
	private String isin;
	
	@Column(name="amf_code")
	private String amfCode;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "stock_type_id", nullable = false)
	private StockType stockType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "server_user_id", nullable = false)
	private User user;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "stock_id")
	private List<StockTicker> listStockTicker;

	@Column(name = "first_input")
	private LocalDateTime firstInput;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;

	@Column(name = "greenrj_listed")
	private Boolean greenrjListed;

	@Column(name = "mystocks_listed")
	private Boolean mystocksListed;

	/**
	 * @param id
	 */
	public Stock(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * 
	 */
	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @author sauzanne @return the listStockTicker
	 */
	public List<StockTicker> getListStockTicker() {
		return listStockTicker;
	}

	/**
	 * @author sauzanne @param listStockTicker the listStockTicker to set
	 */
	public void setListStockTicker(List<StockTicker> listStockTicker) {
		this.listStockTicker = listStockTicker;
	}

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin
	 *            the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the amfCode
	 */
	public String getAmfCode() {
		return amfCode;
	}

	/**
	 * @param amfCode the amfCode to set
	 */
	public void setAmfCode(String amfCode) {
		this.amfCode = amfCode;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the stockType
	 */
	public StockType getStockType() {
		return stockType;
	}

	/**
	 * @param stockType
	 *            the stockType to set
	 */
	public void setStockType(StockType stockType) {
		this.stockType = stockType;
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
	 * @return the greenrjListed
	 */
	public Boolean getGreenrjListed() {
		return greenrjListed;
	}

	/**
	 * @param greenrjListed
	 *            the greenrjListed to set
	 */
	public void setGreenrjListed(Boolean greenrjListed) {
		this.greenrjListed = greenrjListed;
	}

	/**
	 * @return the mystocksListed
	 */
	public Boolean getMystocksListed() {
		return mystocksListed;
	}

	/**
	 * @param mystocksListed
	 *            the mystocksListed to set
	 */
	public void setMystocksListed(Boolean mystocksListed) {
		this.mystocksListed = mystocksListed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstInput == null) ? 0 : firstInput.hashCode());
		result = prime * result + ((greenrjListed == null) ? 0 : greenrjListed.hashCode());
		result = prime * result + id;
		result = prime * result + ((isin == null) ? 0 : isin.hashCode());
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((mystocksListed == null) ? 0 : mystocksListed.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((stockType == null) ? 0 : stockType.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Stock other = (Stock) obj;
		if (firstInput == null) {
			if (other.firstInput != null)
				return false;
		} else if (!firstInput.equals(other.firstInput))
			return false;
		if (greenrjListed == null) {
			if (other.greenrjListed != null)
				return false;
		} else if (!greenrjListed.equals(other.greenrjListed))
			return false;
		if (id != other.id)
			return false;
		if (isin == null) {
			if (other.isin != null)
				return false;
		} else if (!isin.equals(other.isin))
			return false;
		if (lastModified == null) {
			if (other.lastModified != null)
				return false;
		} else if (!lastModified.equals(other.lastModified))
			return false;
		if (mystocksListed == null) {
			if (other.mystocksListed != null)
				return false;
		} else if (!mystocksListed.equals(other.mystocksListed))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stockType == null) {
			if (other.stockType != null)
				return false;
		} else if (!stockType.equals(other.stockType))
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

}
