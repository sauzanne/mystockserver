/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.valuation;

import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.valuation.Valuation;

/**
 * @author sauzanne
 *
 */
@Repository("valuationDao")
public class ValuationDaoImpl extends AbstractDaoImpl<Valuation> implements ValuationDao<Valuation> {


}
