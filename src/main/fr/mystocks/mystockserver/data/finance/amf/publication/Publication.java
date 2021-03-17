package fr.mystocks.mystockserver.data.finance.amf.publication;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.mystocks.mystockserver.data.finance.amf.publicationtype.PublicationType;
import fr.mystocks.mystockserver.data.finance.stock.Stock;

@Entity
@Table(name = "mystocks.amf_publication")
public class Publication implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8037838925953393701L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "date_publication")
	private LocalDate datePublication;

	@ManyToOne
	@JoinColumn(name = "amf_publication_type_id", nullable = false)
	private PublicationType publicationType;
	
	@ManyToOne
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stock;

	
	@Column(name = "link")
	private String link;
	
	@Column(name="doc_bdif")
	private String docBdif;

	public Publication() {
		super();
	}


	public Publication(LocalDate datePublication, PublicationType publicationType, Stock stock, String link,
			String docBdif) {
		super();
		this.datePublication = datePublication;
		this.publicationType = publicationType;
		this.stock = stock;
		this.link = link;
		this.docBdif = docBdif;
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
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the datePublication
	 */
	public LocalDate getDatePublication() {
		return datePublication;
	}

	/**
	 * @param datePublication the datePublication to set
	 */
	public void setDatePublication(LocalDate datePublication) {
		this.datePublication = datePublication;
	}

	/**
	 * @return the publicationType
	 */
	public PublicationType getPublicationType() {
		return publicationType;
	}

	/**
	 * @param publicationType the publicationType to set
	 */
	public void setPublicationType(PublicationType publicationType) {
		this.publicationType = publicationType;
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
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public int hashCode() {
		return Objects.hash(datePublication, id, link, publicationType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publication other = (Publication) obj;
		return Objects.equals(datePublication, other.datePublication) && Objects.equals(id, other.id)
				&& Objects.equals(link, other.link) && Objects.equals(publicationType, other.publicationType);
	}
	
	
	

}
