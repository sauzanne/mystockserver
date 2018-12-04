/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.equities;

import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.Equities;

/**
 * @author sauzanne
 *
 */
@Repository("equitiesDao")
public class EquitiesDaoImpl extends AbstractDaoImpl<Equities> implements EquitiesDao<Equities> {


}
