package fr.mystocks.mystockserver.service.finance.amf;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import fr.mystocks.mystockserver.dao.finance.amf.notification.NotificationDao;
import fr.mystocks.mystockserver.dao.finance.amf.publication.PublicationDao;
import fr.mystocks.mystockserver.dao.finance.amf.publicationtype.PublicationTypeDao;
import fr.mystocks.mystockserver.dao.finance.stock.StockDao;
import fr.mystocks.mystockserver.dao.finance.stockticker.StockTickerDao;
import fr.mystocks.mystockserver.dao.security.account.AccountDao;
import fr.mystocks.mystockserver.data.finance.amf.notification.Notification;
import fr.mystocks.mystockserver.data.finance.amf.publication.Publication;
import fr.mystocks.mystockserver.data.finance.amf.publicationtype.PublicationType;
import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.service.finance.amf.constant.AmfAddDeleteEnum;
import fr.mystocks.mystockserver.service.finance.amf.publication.PublicationService;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.date.DateTools;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.http.HttpTools;
import fr.mystocks.mystockserver.technic.mail.MailTools;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;
import fr.mystocks.mystockserver.view.model.finance.amf.AmfStockModel;

@Service("amfService")
@Transactional
public class AmfServiceImpl implements AmfService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String AMF_LIST_COMPANIES_URL = "https://bdif.amf-france.org/technique/bdif-societes";

	private static final String LIMIT = "limit";
	private static final String Q = "q";
	private static final String TIMESTAMP = "timestamp";

	private static final String PARIS_CODE_PLACE = "PA";

	private static final LocalDate PUBLICATION_START_SEARCH = LocalDate.of(2021, 3, 1);

	private static final String STOCK_TYPE_DEFAULT_CODE = "stocks.type.default";

	@Autowired
	private PropertiesTools propertiesTools;

	@Autowired
	private StockDao<Stock> stockDao;

	@Autowired
	private PublicationDao<Publication> publicationDao;

	@Autowired
	private PublicationTypeDao<PublicationType> publicationTypeDao;

	@Autowired
	private NotificationDao<Notification> notificationDao;

	@Autowired
	private PublicationService publicationService;

	@Autowired
	private MailTools mailTools;

	@Autowired
	private StockTickerDao<StockTicker> stockTickerDao;

	@Autowired
	private AccountDao<Account> accountDao;

	private final static String MAIL_ADMIN = "sauzanne@gmail.com";

	@Override
	public Integer subscribeAmfAlert(String login, String codeStockTicker, AmfAddDeleteEnum addDelete) {

		try {
			StockTicker stockTicker = stockTickerDao.findByCodeAndPlace(codeStockTicker, PARIS_CODE_PLACE, true);

			if (stockTicker == null) {
				throw new FunctionalException(this, "error.finance.stockticker.notfound");
			}

			Account account = accountDao.getAccountByLogin(login);
			if (account == null) {
				throw new FunctionalException(this, "error.account.login");
			}

			List<Notification> notifications = notificationDao.findNotification(stockTicker.getStock().getId(),
					account.getId());

			if (!notifications.isEmpty() && addDelete == AmfAddDeleteEnum.Add) {
				throw new FunctionalException(this, "error.finance.alert.exist");
			} else if (notifications.isEmpty() && addDelete == AmfAddDeleteEnum.Delete) {
				throw new FunctionalException(this, "error.finance.alert.notexist");
			}

			if (addDelete == AmfAddDeleteEnum.Add) {

				Notification n = new Notification();

				n.setAccount(account);
				n.setStock(stockTicker.getStock());

				notificationDao.create(n);

				return n.getId();
			} else {
				notifications.stream().forEach(n -> notificationDao.delete(n));
			}

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

	@Override
	public String getCodeAmf(Stock stock, StringBuffer error) {
		int limit = 1000;
		try {
			String jsonResponse = HttpTools.getURLWithHeaders(AMF_LIST_COMPANIES_URL + TechnicalConstant.QUESTION + Q
					+ TechnicalConstant.EQUALS + URLEncoder.encode(stock.getName(), TechnicalConstant.ENCODING)
					+ TechnicalConstant.AMPERSAND_SEPARATOR + LIMIT + TechnicalConstant.EQUALS + limit
					+ TechnicalConstant.AMPERSAND_SEPARATOR + TIMESTAMP + TechnicalConstant.EQUALS
					+ DateTools.getEpoch(LocalDateTime.now()), null);

			if (!Strings.isNullOrEmpty(jsonResponse)) {
				String cleanResponse = StringEscapeUtils.unescapeJava(jsonResponse.trim());
				List<String> reponses = Arrays.asList(cleanResponse.split("\\n")).stream()
						.filter(r -> !r.trim().isEmpty()).collect(Collectors.toList());

				if (reponses.size() > 1) {
					String warning = "WARNING : multiple response for stock : " + stock.getName() + "("
							+ stock.getIsin() + ")\n";
					logger.error(warning);
					error.append(warning);
				}

				String[] amfCode = reponses.get(0).split("\\|");

				return amfCode[1].trim();
			}

		} catch (Exception e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

	@Override
	public String getResult(Stock stock, LocalDate fromDate) {
		try {
			String jsonResponse = HttpTools.getURLWithHeaders(
					"https://bdif.amf-france.org/Resultat-de-recherche-BDIF?formId=BDIF&DOC_TYPE=BDIF&LANGUAGE=fr&subFormId=as&BDIF_RAISON_SOCIALE="
							+ URLEncoder.encode(stock.getName(), TechnicalConstant.ENCODING) + "&bdifJetonSociete="
							+ stock.getAmfCode() + "&DATE_PUBLICATION="
							+ DateTools.convertLocalDateToFormat(fromDate, DateTools.FRENCH_DATE_FORMATTER)
							+ "&DATE_OBSOLESCENCE=&valid_form=Lancer+la+recherche&isSearch=true",
					null);

			Document jsoupDocument = Jsoup.parse(jsonResponse);

			Elements links = jsoupDocument.select("a[href]");

			List<String> listLink = links.stream()
					.filter(l -> l.attr("href").startsWith("/technique/proxy-lien?docId=")).map(l -> l.attr("href"))
					.collect(Collectors.toList());

			List<Publication> publicationsToSend = new ArrayList<>();
			for (String link : listLink) {
				Publication p = publicationService.getDownloadPage(link, stock);
				if (p != null) {
					publicationsToSend.add(p);
				}
			}

			for (Publication p : publicationsToSend) {
				sendPublication(p);
			}

		} catch (Exception e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}

	private void sendPublication(Publication p) {

		List<Account> accounts = notificationDao.findSubscriber(p.getStock().getId(), null);

		if (!accounts.isEmpty()) {

			String subject = propertiesTools.getProperty("amf.mail.subject", new String[] { p.getStock().getName() });

			String datePublication = DateTools.convertLocalDateToFormat(p.getDatePublication(),
					DateTools.FRENCH_DATE_FORMATTER);
			for (Account a : accounts) {
				String body = propertiesTools.getProperty("amf.mail.body",
						new String[] { a.getFirstName(), p.getStock().getName(), datePublication,
								p.getPublicationType().getPublicationType(), AmfService.AMF_BASE_URL + p.getLink() });
				try {
					mailTools.sendMessage(a.getMail(), null, subject, body, null, true);
				} catch (RuntimeException e) {
					ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
				}

			}
		}

	}

	@Scheduled(cron = "${cron.amfupdate.publication}")
	@Override
	public void cronAmfUpdatePublication() {
		logger.error("Amf update publication started at " + LocalDateTime.now());

		// étape 1 : recherche et mise à jour des actions

		// on recherche que les valeurs "FR"
		List<Stock> listStockFR = stockDao.findByIsin("FR");

		// on prend la liste des valeurs à 'updater'
		List<Stock> listStockToUpdatePublication = listStockFR.stream()
				.filter(s -> s.getAmfCode() != null && !s.isAmfNoUpdate() && s.getStockType().getCode().equals(STOCK_TYPE_DEFAULT_CODE))
				.collect(Collectors.toList());

		StringBuffer error = new StringBuffer();
		for (Stock stock : listStockToUpdatePublication) {
			try {
				LocalDate lastPublicationDate = publicationDao.findLastPublicationDateByStock(stock.getId());

				if (lastPublicationDate == null) {
					lastPublicationDate = PUBLICATION_START_SEARCH;
				}

				getResult(stock, lastPublicationDate);

			} catch (Exception e) {
				error.append("\nImpossible to get last publication for  " + stock.getName() + "(" + stock.getIsin()
						+ ")\n" + e.getLocalizedMessage());
				ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
			}
		}

		String amfStats = "\nAmf update publication report :\n" + error.toString() + "\nAmf update ended at "
				+ LocalDateTime.now();
		if (error.length() > 0) {

			mailTools.sendMessage(MAIL_ADMIN,
					"Amf update publication ended at " + LocalDateTime.now() + " with success", amfStats);
		}

		logger.error(amfStats);

	}

	@Scheduled(cron = "${cron.amfupdate}")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void cronAmfUpdate() {
		logger.error("Amf update started at " + LocalDateTime.now());

		// étape 1 : recherche et mise à jour des actions

		// on recherche que les valeurs "FR"
		List<Stock> listStockFR = stockDao.findByIsin("FR");

		// on prend la liste des valeurs à 'updater'
		List<Stock> listStockToUpdate = listStockFR.stream()
				.filter(s -> s.getAmfCode() == null && s.getStockType().getCode().equals(STOCK_TYPE_DEFAULT_CODE))
				.collect(Collectors.toList());

		StringBuffer error = new StringBuffer();
		for (Stock stock : listStockToUpdate) {
			try {
				String codeAmf = getCodeAmf(stock, error);

				if (codeAmf != null) {
					stock.setLastModified(LocalDateTime.now());
					stock.setAmfCode(codeAmf);
					stockDao.update(stock);
				} else {
					String errorAmfCode = "Impossible to get AMF code for " + stock.getName() + "(" + stock.getIsin()
							+ ")\n";
					error.append(errorAmfCode);
					logger.error(errorAmfCode);
				}
			} catch (Exception e) {
				ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
			}
		}
		String amfStats = "\nAmf update report :\n" + error.toString() + "\nAmf update ended at " + LocalDateTime.now();

		if (error.length() > 0) {

			mailTools.sendMessage(MAIL_ADMIN, "Amf update ended at " + LocalDateTime.now() + " with success", amfStats);
		}

		logger.error(amfStats);

	}

	@Override
	public List<AmfStockModel> getAllMeasure(String login) {
		try {
			Account account = accountDao.getAccountByLogin(login);
			if (account == null) {
				throw new FunctionalException(this, "error.account.login");
			}

			List<Notification> notifications = notificationDao.findNotification(null, account.getId());

			List<AmfStockModel> listStockModel = notifications.stream().map(n -> n.getStock())
					.map(s -> new AmfStockModel(s.getIsin(),
							s.getListStockTicker().stream().findFirst().orElse(new StockTicker()).getCode(),
							s.getName()))
					.collect(Collectors.toList());

			return listStockModel;

		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;
	}

}
