package fr.mystocks.mystockserver.service.finance.amf;

import java.time.LocalDate;

import fr.mystocks.mystockserver.data.finance.stock.Stock;

public interface AmfService {

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

}
