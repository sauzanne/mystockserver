package fr.mystocks.mystockserver.dao.finance.measure;

import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.measure.Measure;

public interface MeasureDao<T> extends Dao<T> {

	List<Measure> getMeasuresByCode(List<String> listCodes);

	/**
	 * Ne récupère que les mesures qui sont considérées comme mise à disposition
	 * 
	 * @return la liste des mesures disponibles
	 */
	List<Measure> getAvailableMeasures();

}
