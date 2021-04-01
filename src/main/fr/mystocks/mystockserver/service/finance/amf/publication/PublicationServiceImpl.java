package fr.mystocks.mystockserver.service.finance.amf.publication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import fr.mystocks.mystockserver.dao.finance.amf.publication.PublicationDao;
import fr.mystocks.mystockserver.dao.finance.amf.publicationtype.PublicationTypeDao;
import fr.mystocks.mystockserver.data.finance.amf.publication.Publication;
import fr.mystocks.mystockserver.data.finance.amf.publicationtype.PublicationType;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.service.finance.amf.AmfService;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.http.HttpTools;

@Service("publicationService")
public class PublicationServiceImpl implements PublicationService {
	
	@Autowired
	private PublicationDao<Publication> publicationDao;
	
	@Autowired
	private PublicationTypeDao<PublicationType> publicationTypeDao;

	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Publication getDownloadPage(String link, Stock stock) {
		try {
			String jsonResponse = HttpTools.getURLWithHeaders(AmfService.AMF_BASE_URL + link, null);

			Document jsoupDocument = Jsoup.parse(jsonResponse);
			Elements spanDocPDIF = jsoupDocument.select("span[class*=docBDIF]");
			Elements divTitreBdif = jsoupDocument.select("div[name*=titreBdif]");

			Optional<Element> optSpanPublishAt = jsoupDocument.select("span").stream()
					.filter(e -> e.childNodeSize() > 0 && e.childNode(0).outerHtml().contains("Publié le")).findFirst();
			LocalDate publicationDate = null;
			String publicationDateText = null;
			String docBDIF = null;
			String publicationTypeText = null;

			if (spanDocPDIF.get(0).childNode(0).outerHtml().indexOf(AmfService.DU) != -1) {
				String[] spanDocPDIFContentSplitted = spanDocPDIF.get(0).childNode(0).outerHtml().split(AmfService.DU);

				publicationDateText = spanDocPDIFContentSplitted[1].trim();
				docBDIF = spanDocPDIFContentSplitted[0].split("n°")[1].trim();
				publicationTypeText = divTitreBdif.get(0).childNode(0).outerHtml().split(",")[0].trim();
			} else if (optSpanPublishAt.isPresent()) {
				publicationDateText = optSpanPublishAt.get().text().split(":")[1].trim();
				publicationTypeText = jsoupDocument.select("h3[class*=bdif]").first().text().trim();

			}

			if (!Strings.isNullOrEmpty(docBDIF) && publicationDao.findPublicationByBDIFCode(docBDIF) != null) {
				logger.debug("Publication with BDIFCode " + docBDIF + " already exist !");
				return null;
			}

			try {
				publicationDate = LocalDate.parse(publicationDateText,
						DateTimeFormatter.ofPattern("dd MMMM u", Locale.FRANCE));
			} catch (DateTimeParseException e) {
				publicationDate = LocalDate.parse(publicationDateText,
						DateTimeFormatter.ofPattern("d MMMM u", Locale.FRANCE));

			}

			String linkPDF = jsoupDocument.select("a[title*=Consulter le document]").attr("href").toString();

			PublicationType publicationType = publicationTypeDao.findByName(publicationTypeText);

			if (publicationType == null) {
				publicationType = new PublicationType(publicationTypeText);
				publicationTypeDao.create(publicationType);
			}

			Publication publication = new Publication(publicationDate, publicationType, stock, linkPDF, docBDIF, LocalDateTime.now());

			publicationDao.create(publication);

			return publication;
		} catch (Exception e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

}
