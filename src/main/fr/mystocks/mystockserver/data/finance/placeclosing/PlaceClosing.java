package fr.mystocks.mystockserver.data.finance.placeclosing;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.mystocks.mystockserver.data.finance.place.Place;

@Entity
@Table(name="mystocks.place_closing")
public class PlaceClosing implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3634416590089701738L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	
    @Column(name = "date_closing")
    private LocalDate closing;

	@ManyToOne
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the closingDate
	 */
	public LocalDate getClosing() {
		return closing;
	}

	/**
	 * @param closing the closingDate to set
	 */
	public void setClosing(LocalDate closing) {
		this.closing = closing;
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
	 * 
	 */
	public PlaceClosing() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((closing == null) ? 0 : closing.hashCode());
		result = prime * result + id;
		result = prime * result + ((place == null) ? 0 : place.hashCode());
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
		PlaceClosing other = (PlaceClosing) obj;
		if (closing == null) {
			if (other.closing != null)
				return false;
		} else if (!closing.equals(other.closing))
			return false;
		if (id != other.id)
			return false;
		if (place == null) {
			if (other.place != null)
				return false;
		} else if (!place.equals(other.place))
			return false;
		return true;
	}


}
