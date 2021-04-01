/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.amf.publication;

import java.time.LocalDate;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.amf.publication.Publication;

/**
 * @author sauzanne
 *
 */
@Repository("publicationDao")
public class PublicationDaoImpl extends AbstractDaoImpl<Publication> implements PublicationDao<Publication> {
	private static final String BIND_STOCK_ID = "stockId";
	private static final String BIND_BDIF = "bdifCode";


	@Override
	public LocalDate findLastPublicationDateByStock(Integer stockId) {
		StringBuilder request = new StringBuilder();

		request.append("select max(p.datePublication) from Publication p");


		request.append(" where p.stock.id=:" + BIND_STOCK_ID);
		request.append(" order by p.datePublication desc");

		Query query = getSession().createQuery(request.toString());
		
		query.setParameter(BIND_STOCK_ID, stockId);

		return (LocalDate) query.uniqueResult();
	}
	
	@Override
	public Publication findPublicationByBDIFCode(String code) {
		StringBuilder request = new StringBuilder();

		request.append("select p from Publication p");


		request.append(" where p.docBdif=:" + BIND_BDIF);
		request.append(" order by p.datePublication desc");

		Query query = getSession().createQuery(request.toString());
		
		query.setParameter(BIND_BDIF, code);

		return (Publication) query.uniqueResult();
	}


}
