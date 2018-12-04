/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.measurecalculation;

import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.measurecalculation.MeasureCalculation;

/**
 * @author sauzanne
 *
 */
@Repository("measureCalculationDao")
public class MeasureCalculationDaoImpl extends AbstractDaoImpl<MeasureCalculation> implements MeasureCalculationDao<MeasureCalculation> {
}
