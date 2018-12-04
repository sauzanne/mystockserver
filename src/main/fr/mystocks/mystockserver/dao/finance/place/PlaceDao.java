package fr.mystocks.mystockserver.dao.finance.place;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.place.Place;

public interface PlaceDao<T> extends Dao<T> {
	
    Place findByCode(String code);

}
