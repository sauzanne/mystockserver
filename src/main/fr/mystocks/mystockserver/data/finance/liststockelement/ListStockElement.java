package fr.mystocks.mystockserver.data.finance.liststockelement;

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

import fr.mystocks.mystockserver.data.finance.liststock.ListStock;
import fr.mystocks.mystockserver.data.finance.stock.Stock;

@Entity
@Table(name = "mystocks.list_stock_element")
public class ListStockElement implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7848708681696239477L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "list_stock_id", nullable = false)
    private ListStock listStock;


    @Column(name = "first_input")
    private LocalDateTime firstInput;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public ListStock getListStock() {
		return listStock;
	}

	public void setListStock(ListStock listStock) {
		this.listStock = listStock;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstInput == null) ? 0 : firstInput.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
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
		ListStockElement other = (ListStockElement) obj;
		if (firstInput == null) {
			if (other.firstInput != null)
				return false;
		} else if (!firstInput.equals(other.firstInput))
			return false;
		if (id != other.id)
			return false;
		if (lastModified == null) {
			if (other.lastModified != null)
				return false;
		} else if (!lastModified.equals(other.lastModified))
			return false;
		return true;
	}

   

}
