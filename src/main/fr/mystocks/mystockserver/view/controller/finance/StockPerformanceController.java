
package fr.mystocks.mystockserver.view.controller.finance;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;
import fr.mystocks.mystockserver.service.finance.performance.StockPerformanceService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;
import fr.mystocks.mystockserver.view.model.finance.stockperformance.StockPerformanceModel;

@Path("finance/stockperformance")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class StockPerformanceController {

	private static final String PARAM_CODE = "c";

	private static final String PARAM_PLACE = "p";

	private static final String PARAM_START_YEAR = "sy";

	private static final String PARAM_END_YEAR = "ey";

	private static final String PARAM_PERIOD = "pe";

	private static final String PARAM_PREVIOUS_START_YEAR = "psy";
	private static final String PARAM_PREVIOUS_END_YEAR = "pey";
	private static final String PARAM_PREVIOUS_PERIOD = "ppe";

	private static final String MEASURES = "m";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private StockPerformanceService stockPerformanceService;

	@Inject
	private SpringConfiguration context;

	@RolesAllowed(RoleConst.READONLY_USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@GET
	@Path("getPerformance")
	public Response getPerformance(@QueryParam(PARAM_CODE) String codeStockTicker,
			@QueryParam(PARAM_PLACE) String placeCode, @QueryParam(PARAM_START_YEAR) Integer startYear,
			@QueryParam(PARAM_END_YEAR) Integer endYear, @QueryParam(PARAM_PERIOD) String period,
			@QueryParam(PARAM_PREVIOUS_START_YEAR) Integer previousStartYear,
			@QueryParam(PARAM_PREVIOUS_END_YEAR) Integer previousEndYear,
			@QueryParam(PARAM_PREVIOUS_PERIOD) String previousPeriod, @QueryParam(MEASURES) String measures) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(codeStockTicker, PARAM_CODE);
		controllerMessageTools.checkEmptyParameter(placeCode, PARAM_PLACE);
		controllerMessageTools.checkEmptyParameter(startYear, PARAM_START_YEAR);
		controllerMessageTools.checkEmptyParameter(period, PARAM_PERIOD);
		
		/* si l'année précédente est non nulle alors le paramètre de période de la période précédente devient obligatoire */
		if(previousStartYear!=null) {
			
				controllerMessageTools.checkEmptyParameter(previousPeriod, PARAM_PREVIOUS_PERIOD);
			
		}
		

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		PeriodEnum periodEnum = PeriodEnum.valueOf(period);

		PeriodEnum previousPeriodEnum = null;

		if (!Strings.isNullOrEmpty(previousPeriod)) {
			previousPeriodEnum = PeriodEnum.valueOf(previousPeriod);
		}

		try {
			List<StockPerformanceModel> listStockPerformanceModel = stockPerformanceService
					.comparePerformanceBetween2Periods(codeStockTicker, placeCode, startYear, endYear, periodEnum,
							previousStartYear, previousEndYear, previousPeriodEnum, measures);
			return Response.ok(listStockPerformanceModel, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), e.getArgs(), context.getLocale()));
			return responseForError.build();
		} catch (RuntimeException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}