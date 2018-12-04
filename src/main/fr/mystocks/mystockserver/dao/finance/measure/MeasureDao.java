package fr.mystocks.mystockserver.dao.finance.measure;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.measure.Measure;

public interface MeasureDao<T> extends Dao<T> {
	
	List<Measure> getMeasuresByCode(List<String> listCodes);
    	
}
