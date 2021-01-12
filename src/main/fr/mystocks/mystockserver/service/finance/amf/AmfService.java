package fr.mystocks.mystockserver.service.finance.amf;

import fr.mystocks.mystockserver.data.finance.stock.Stock;

public interface AmfService {

	String getResult(String valueName, String jetonSociete);

	void cronAmfUpdate();

	String getCodeAmf(Stock stock);

}
