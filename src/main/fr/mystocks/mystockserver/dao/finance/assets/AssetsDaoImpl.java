/**
 * 
 */
package fr.mystocks.mystockserver.dao.finance.assets;

import org.springframework.stereotype.Repository;

import fr.mystocks.mystockserver.dao.AbstractDaoImpl;
import fr.mystocks.mystockserver.data.finance.assets.Assets;

/**
 * @author sauzanne
 *
 */
@Repository("assetsDao")
public class AssetsDaoImpl extends AbstractDaoImpl<Assets> implements AssetsDao<Assets> {


}
