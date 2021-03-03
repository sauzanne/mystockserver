package fr.mystocks.mystockserver.service.finance.amf;

import fr.mystocks.mystockserver.data.finance.stock.Stock;

public interface AmfService {

	String getResult(String valueName, String jetonSociete);

	void cronAmfUpdate();

	/**
	 * Get Amf Code
	 * @param stock stock to get amfcode
	 * @param error buffer for error
	 * @return the amf code
	 */
	String getCodeAmf(Stock stock, StringBuffer error);

}
