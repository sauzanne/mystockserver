package fr.mystocks.mystockserver.data.finance.amf.publicationtype;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mystocks.amf_publication_type")
public class PublicationType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4171709766468755209L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	


	@Column(name = "publication_type")
	private String publicationType;
	
	

	public PublicationType(String publicationType) {
		super();
		this.publicationType = publicationType;
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
	 * @return the publicationType
	 */
	public String getPublicationType() {
		return publicationType;
	}

	/**
	 * @param publicationType the publicationType to set
	 */
	public void setPublicationType(String publicationType) {
		this.publicationType = publicationType;
	}

	public PublicationType() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, publicationType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicationType other = (PublicationType) obj;
		return Objects.equals(id, other.id) && Objects.equals(publicationType, other.publicationType);
	}

}
