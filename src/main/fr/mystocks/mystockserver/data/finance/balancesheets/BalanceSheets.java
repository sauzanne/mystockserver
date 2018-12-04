package fr.mystocks.mystockserver.data.finance.balancesheets;

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

import fr.mystocks.mystockserver.data.finance.Equities;
import fr.mystocks.mystockserver.data.finance.assets.Assets;
import fr.mystocks.mystockserver.data.finance.liabilities.Liabilities;
import fr.mystocks.mystockserver.data.security.Account;

@Entity
@Table(name = "mystocks.balance_sheets")
public class BalanceSheets implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1577021086165484640L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "equities_id", nullable = true)
	private Equities equities;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "liabilities_id", nullable = true)
	private Liabilities liabilities;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assets_id", nullable = true)
	private Assets assets;

	@Column(name = "first_input")
	private LocalDateTime firstInput;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;

	/**
	 * @param id
	 */
	public BalanceSheets(Integer id) {
		super();
		this.id = id;
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

	public Equities getEquities() {
		return equities;
	}

	public void setEquities(Equities equities) {
		this.equities = equities;
	}

	public Liabilities getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(Liabilities liabilities) {
		this.liabilities = liabilities;
	}

	public Assets getAssets() {
		return assets;
	}

	public void setAssets(Assets assets) {
		this.assets = assets;
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

	public BalanceSheets(int id, Equities equities, Liabilities liabilities, Assets assets, Account account,
			LocalDateTime firstInput, LocalDateTime lastModified) {
		super();
		this.id = id;
		this.equities = equities;
		this.liabilities = liabilities;
		this.assets = assets;
		this.firstInput = firstInput;
		this.lastModified = lastModified;
	}

	/**
	 * 
	 */
	public BalanceSheets() {
		super();
		// TODO Auto-generated constructor stub
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
		result = prime * result + id;
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
		BalanceSheets other = (BalanceSheets) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
