package fr.mystocks.mystockserver.test.measure;

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

import fr.mystocks.mystockserver.service.finance.measures.MeasureCalculationService;
import fr.mystocks.mystockserver.service.finance.measures.MeasureService;
import fr.mystocks.mystockserver.technic.configuration.start.SpringBootWebApplication;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootWebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/config/application-test.properties")
public class MeasureTest {
	@Autowired
	private MeasureService measureService;
	
	@Autowired
	private MeasureCalculationService measureCalculationService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Test
	public void testCronMeasureCalculation() {
		try {
			measureCalculationService.cronMeasureCalculation();
			return;
		} catch (Exception e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		}
		Assert.fail();
	}


	@Test
	public void testCronMeasureAlert() {
		try {
			measureService.cronMeasureAlert();
			return;
		} catch (Exception e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
		}
		Assert.fail();
	}

}
