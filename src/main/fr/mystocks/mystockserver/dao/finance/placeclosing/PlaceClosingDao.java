package fr.mystocks.mystockserver.dao.finance.placeclosing;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.placeclosing.PlaceClosing;

public interface PlaceClosingDao<T> extends Dao<T> {

	List<PlaceClosing> findByCodePlace(String code);
	
}
