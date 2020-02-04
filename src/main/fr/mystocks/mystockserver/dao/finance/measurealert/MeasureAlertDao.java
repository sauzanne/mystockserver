package fr.mystocks.mystockserver.dao.finance.measurealert;

import java.math.BigDecimal;
import java.util.List;

import fr.mystocks.mystockserver.dao.Dao;
import fr.mystocks.mystockserver.data.finance.measurealert.MeasureAlert;

public interface MeasureAlertDao<T> extends Dao<T> {

	/**
	 * Retourne toutes des alertes non déclenchées
	 * @return une liste des alertes non déclenchées
	 */
	List<MeasureAlert> getNonTriggeredMeasures();

	List<MeasureAlert> findMeasureAlert(Integer accountId, Integer stockTickerId, Integer measureId,
			Integer measureIdCompared, BigDecimal value, String binaryOperator, Boolean triggered);
    	
}
