package fr.mystocks.mystockserver.test.amf;

import org.assertj.core.util.Strings;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.service.finance.amf.AmfService;
import fr.mystocks.mystockserver.service.finance.amf.publication.PublicationService;
import fr.mystocks.mystockserver.technic.configuration.start.SpringBootWebApplication;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootWebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/config/application-test.properties")
public class AmfTest {
	@Autowired
	private AmfService amfService;

	@Autowired
	private PublicationService publicationService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testLVMH() {
		Stock s = new Stock();
		s.setName("LVMH MOET HENNESSY-LOUIS VUITTON");

		publicationService.getDownloadPage(
				"/Fiche-BDIF?xtcr=1&isSearch=true&docId=10S-3110-02.C_C.20-0113-S01&lastSearchPage=https%3A%2F%2Fbdif.amf-france.org%2FmagnoliaPublic%2Famf%2FResultat-de-recherche-BDIF%3FformId%3DBDIF%26DOC_TYPE%3DBDIF%26LANGUAGE%3Dfr%26subFormId%3Das%26BDIF_RAISON_SOCIALE%3DLVMH%2BMOET%2BHENNESSY-LOUIS%2BVUITTON%26bdifJetonSociete%3DRS00001689%26DATE_PUBLICATION%3D%26DATE_OBSOLESCENCE%3D%26valid_form%3DLancer%2Bla%2Brecherche%26isSearch%3Dtrue&xtmc=-LVMH-MOET-HENNESSY-LOUIS-VUITTON",
				s);
	}

	@Test
	public void testRecuperationValeursAmf() {
		try {
			Stock s = new Stock();
			s.setName("BIGBEN INTERACTIVE");
			StringBuffer error = new StringBuffer();
			String isin = amfService.getCodeAmf(s, error);
			Assert.assertTrue(!Strings.isNullOrEmpty(isin));
			return;
		} catch (Exception e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		}
		Assert.fail();
	}

	@Test
	public void testCronAmfUpdate() {
		try {
			amfService.cronAmfUpdate();
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		}
		Assert.fail();
	}

	@Test
	public void testCronAmfUpdatePublication() {
		try {
			amfService.cronAmfUpdatePublication();
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		}
		Assert.fail();
	}

}
