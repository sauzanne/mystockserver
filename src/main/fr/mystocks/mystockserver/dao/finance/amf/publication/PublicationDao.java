package fr.mystocks.mystockserver.dao.finance.amf.publication;

import java.time.LocalDate;

import fr.mystocks.mystockserver.dao.Dao;

public interface PublicationDao<T> extends Dao<T> {

	LocalDate findLastPublicationDateByStock(Integer stockId);

}
