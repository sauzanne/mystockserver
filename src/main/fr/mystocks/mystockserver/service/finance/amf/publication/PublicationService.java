package fr.mystocks.mystockserver.service.finance.amf.publication;

import fr.mystocks.mystockserver.data.finance.amf.publication.Publication;
import fr.mystocks.mystockserver.data.finance.stock.Stock;

public interface PublicationService {

	Publication getDownloadPage(String link, Stock stock);

}
