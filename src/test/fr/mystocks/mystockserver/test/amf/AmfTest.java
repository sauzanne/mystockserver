package fr.mystocks.mystockserver.test.amf;

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
import fr.mystocks.mystockserver.technic.configuration.start.SpringBootWebApplication;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootWebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/config/application-test.properties")
public class AmfTest {
	@Autowired
	private AmfService amfService;
		
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Test
	public void testRecuperationValeursAmf() {
		try {
			Stock s = new Stock();
			s.setName("BIGBEN INTERACTIVE");
			amfService.getCodeAmf(s);
			return;
		} catch (Exception e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		}
		Assert.fail();
	}

}
