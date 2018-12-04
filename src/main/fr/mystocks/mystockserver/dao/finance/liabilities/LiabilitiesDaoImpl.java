/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.liabilities;

import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.liabilities.Liabilities;

/**
 * @author sauzanne
 *
 */
@Repository("liabilitiesDao")
public class LiabilitiesDaoImpl extends AbstractDaoImpl<Liabilities> implements LiabilitiesDao<Liabilities> {


}
