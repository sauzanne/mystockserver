/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.amf.publicationtype;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.amf.publicationtype.PublicationType;

/**
 * @author sauzanne
 *
 */
@Repository("publicationTypeDao")
public class PublicationTypeDaoImpl extends AbstractDaoImpl<PublicationType>
		implements PublicationTypeDao<PublicationType> {

	
	@Override
	public PublicationType findByName(String publicationType) {
		Criteria criteria = getSession().createCriteria(PublicationType.class);
		criteria.add(Restrictions.eq("publicationType",publicationType));
		return (PublicationType) criteria.uniqueResult();
	}

}
