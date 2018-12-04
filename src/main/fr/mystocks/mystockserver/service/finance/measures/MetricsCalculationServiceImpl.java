/**
 * 
 */
package fr.mystocks.mystockserver.service.finance.measures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.service.finance.performance.TechnicalAnalysisService;
import fr.mystocks.mystockserver.service.finance.performance.ValuationService;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;

/**
 * @author sauzanne
 *
 */
@Service("MetricsCalculationService")
@Transactional
@PropertySources({ @PropertySource(value = { "classpath:/application.properties" }) })
public class MetricsCalculationServiceImpl implements MetricsCalculationService {

	@Autowired
	private PropertiesTools propertiesTools;
	
	@Autowired
	private TechnicalAnalysisService technicalAnalysisService;
	
	@Autowired
	private ValuationService valuationService;


	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Scheduled(cron = "${cron.metricscalculation}")
	@Override
	public void cronMetricsCalculation() {
	}

}
