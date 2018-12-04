/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.operations;

import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.operations.Operations;

/**
 * @author sauzanne
 *
 */
@Repository("operationsDao")
public class OperationsDaoImpl extends AbstractDaoImpl<Operations> implements OperationsDao<Operations> {

}
