package fr.mystocks.mystockserver.data.finance.stock;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
	
	@Column(name="amf_no_update")
	private boolean amfNoUpdate;

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


	@Override
	public int hashCode() {
		return Objects.hash(amfCode, amfNoUpdate, firstInput, greenrjListed, id, isin, lastModified, listStockTicker,
				mystocksListed, name, stockType, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return Objects.equals(amfCode, other.amfCode) && amfNoUpdate == other.amfNoUpdate
				&& Objects.equals(firstInput, other.firstInput) && Objects.equals(greenrjListed, other.greenrjListed)
				&& Objects.equals(id, other.id) && Objects.equals(isin, other.isin)
				&& Objects.equals(lastModified, other.lastModified)
				&& Objects.equals(listStockTicker, other.listStockTicker)
				&& Objects.equals(mystocksListed, other.mystocksListed) && Objects.equals(name, other.name)
				&& Objects.equals(stockType, other.stockType) && Objects.equals(user, other.user);
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
	 * @return the amfNoUpdate
	 */
	public boolean isAmfNoUpdate() {
		return amfNoUpdate;
	}

	/**
	 * @param amfNoUpdate the amfNoUpdate to set
	 */
	public void setAmfNoUpdate(boolean amfNoUpdate) {
		this.amfNoUpdate = amfNoUpdate;
	}

}
