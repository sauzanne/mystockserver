package fr.mystocks.mystockserver.test;

import java.time.LocalDate;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import fr.mystocks.mystockserver.data.finance.place.Place;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.service.finance.performance.TechnicalAnalysisService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.configuration.start.SpringBootWebApplication;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootWebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/config/application-test.properties")
public class TechnicalAnalysisTest {

	@Autowired
	private TechnicalAnalysisService technicalAnalysisService;

	@Autowired
	private MessageSource messageSource;

	@Inject
	private SpringConfiguration context;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void myFirstTest() {
		try {
			StockTicker st = new StockTicker("ora", new Place("PA"));
			technicalAnalysisService.getMovingAverage(st, 10, LocalDate.of(2018, 11, 13), 0.1);
			return;
		} catch (FunctionalException e) {
			logger.debug(messageSource.getMessage(e.getKeyError(), e.getArgs(), context.getLocale()));
		} catch (RuntimeException e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		}
		Assert.fail();
	}

}