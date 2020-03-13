package fr.mystocks.mystockserver.test.currency;

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

import fr.mystocks.mystockserver.service.finance.currency.CurrencyService;
import fr.mystocks.mystockserver.technic.configuration.start.SpringBootWebApplication;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootWebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/config/application-test.properties")
public class CurrencyServiceTest {
	@Autowired
	private CurrencyService currencyService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testCurrencyService() {
		try {
			currencyService.getCurrentFxRate("EUR", "USD");
			return;
		} catch (Exception e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		}
		Assert.fail();
	}

}
