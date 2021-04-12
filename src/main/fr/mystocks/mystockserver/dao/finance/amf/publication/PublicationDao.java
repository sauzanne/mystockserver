package fr.mystocks.mystockserver.dao.finance.amf.publication;

import java.time.LocalDate;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.amf.publication.Publication;

public interface PublicationDao<T> extends Dao<T> {

	LocalDate findLastPublicationDateByStock(Integer stockId);

	Publication findPublicationByBDIFCode(String code);

	Publication findPublicationByLink(String link);

}
