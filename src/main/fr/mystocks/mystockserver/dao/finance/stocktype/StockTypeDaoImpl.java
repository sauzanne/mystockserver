/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.stocktype;

import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.stocktype.StockType;

/**
 * @author sauzanne
 *
 */
@Repository("stockTypeDao")
public class StockTypeDaoImpl extends AbstractDaoImpl<StockType> implements StockTypeDao<StockType> {
    

}
