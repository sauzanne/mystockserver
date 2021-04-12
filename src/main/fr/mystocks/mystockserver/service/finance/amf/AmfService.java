package fr.mystocks.mystockserver.service.finance.amf;

import java.time.LocalDate;

import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.service.finance.amf.constant.AmfAddDeleteEnum;

public interface AmfService {
	
	static final String AMF_BASE_URL = "https://bdif.amf-france.org";
	static final String DU = "du";


	String getResult(Stock stock, LocalDate fromDate);

	void cronAmfUpdate();

	/**
	 * Get Amf Code
	 * @param stock stock to get amfcode
	 * @param error buffer for error
	 * @return the amf code
	 */
	String getCodeAmf(Stock stock, StringBuffer error);

	void cronAmfUpdatePublication();

	Integer subscribeAmfAlert(String login, String codeStockTicker, AmfAddDeleteEnum addDelete);

}
