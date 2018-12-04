/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.balancesheets;

import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.balancesheets.BalanceSheets;

/**
 * @author sauzanne
 *
 */
@Repository("balanceSheetsDao")
public class BalanceSheetsDaoImpl extends AbstractDaoImpl<BalanceSheets> implements BalanceSheetsDao<BalanceSheets> {

}
