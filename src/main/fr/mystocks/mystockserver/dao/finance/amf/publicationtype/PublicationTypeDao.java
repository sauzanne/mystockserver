package fr.mystocks.mystockserver.dao.finance.amf.publicationtype;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.amf.publicationtype.PublicationType;

public interface PublicationTypeDao<T> extends Dao<T> {

	PublicationType findByName(String name);

}
