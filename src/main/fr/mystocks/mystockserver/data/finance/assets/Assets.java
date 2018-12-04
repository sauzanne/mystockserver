package fr.mystocks.mystockserver.data.finance.assets;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mystocks.assets")
public class Assets implements Serializable {


	public Assets(int id, BigDecimal cash, BigDecimal inventories, BigDecimal currentAssets, BigDecimal goodwill,
			BigDecimal tradeAccounts, LocalDateTime firstInput, LocalDateTime lastModified) {
		super();
		this.id = id;
		this.cash = cash;
		this.inventories = inventories;
		this.currentAssets = currentAssets;
		this.goodwill = goodwill;
		this.tradeAccounts = tradeAccounts;
		this.firstInput = firstInput;
		this.lastModified = lastModified;
	}

	/**
	 * @param id
	 */
	public Assets(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2059571606452858316L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cash")
    private BigDecimal cash;

    @Column(name = "inventories")
    private BigDecimal inventories;
    
    @Column(name = "current_assets")
    private BigDecimal currentAssets;
    
    @Column(name = "goodwill")
    private BigDecimal goodwill;
    
    @Column(name = "trade_accounts")
    private BigDecimal tradeAccounts;


    @Column(name = "first_input")
    private LocalDateTime firstInput;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

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

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getInventories() {
		return inventories;
	}

	public void setInventories(BigDecimal inventories) {
		this.inventories = inventories;
	}

	public BigDecimal getCurrentAssets() {
		return currentAssets;
	}

	public void setCurrentAssets(BigDecimal currentAssets) {
		this.currentAssets = currentAssets;
	}

	public BigDecimal getGoodwill() {
		return goodwill;
	}

	public void setGoodwill(BigDecimal goodwill) {
		this.goodwill = goodwill;
	}

	public BigDecimal getTradeAccounts() {
		return tradeAccounts;
	}

	public void setTradeAccounts(BigDecimal tradeAccounts) {
		this.tradeAccounts = tradeAccounts;
	}

	public LocalDateTime getFirstInput() {
		return firstInput;
	}

	public void setFirstInput(LocalDateTime firstInput) {
		this.firstInput = firstInput;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public Assets() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cash == null) ? 0 : cash.hashCode());
		result = prime * result + ((currentAssets == null) ? 0 : currentAssets.hashCode());
		result = prime * result + ((firstInput == null) ? 0 : firstInput.hashCode());
		result = prime * result + ((goodwill == null) ? 0 : goodwill.hashCode());
		result = prime * result + id;
		result = prime * result + ((inventories == null) ? 0 : inventories.hashCode());
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((tradeAccounts == null) ? 0 : tradeAccounts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assets other = (Assets) obj;
		if (cash == null) {
			if (other.cash != null)
				return false;
		} else if (!cash.equals(other.cash))
			return false;
		if (currentAssets == null) {
			if (other.currentAssets != null)
				return false;
		} else if (!currentAssets.equals(other.currentAssets))
			return false;
		if (firstInput == null) {
			if (other.firstInput != null)
				return false;
		} else if (!firstInput.equals(other.firstInput))
			return false;
		if (goodwill == null) {
			if (other.goodwill != null)
				return false;
		} else if (!goodwill.equals(other.goodwill))
			return false;
		if (id != other.id)
			return false;
		if (inventories == null) {
			if (other.inventories != null)
				return false;
		} else if (!inventories.equals(other.inventories))
			return false;
		if (lastModified == null) {
			if (other.lastModified != null)
				return false;
		} else if (!lastModified.equals(other.lastModified))
			return false;
		if (tradeAccounts == null) {
			if (other.tradeAccounts != null)
				return false;
		} else if (!tradeAccounts.equals(other.tradeAccounts))
			return false;
		return true;
	}
}
