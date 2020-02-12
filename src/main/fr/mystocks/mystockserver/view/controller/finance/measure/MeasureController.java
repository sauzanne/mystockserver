
package fr.mystocks.mystockserver.view.controller.finance.measure;

import java.io.File;
import java.math.BigDecimal;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.service.finance.measures.MeasureService;
import fr.mystocks.mystockserver.service.finance.measures.constant.BinaryOperatorEnum;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;

@Path("finance/measure")
@Component
public class MeasureController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private MeasureService measureService;

	@Inject
	private SpringConfiguration context;

	private final static String PARAM_LOGIN = "l";
	private final static String PARAM_STOCK_TICKER = "st";
	private static final String PARAM_PLACE = "p";

	private static final String PARAM_MEASURE1 = "m1";
	private static final String PARAM_MEASURE2 = "m2";
	private static final String PARAM_VALUE = "v";
	private static final String PARAM_BINARY_OPERATOR = "bo";
	
	private static final String PARAM_COMMENT = "c";

	private static final String PARAM_TRIGGERED = "t";

	@RolesAllowed(RoleConst.USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Path("postCreateMeasureAlert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postOperations(@FormParam(PARAM_LOGIN) String login,
			@FormParam(PARAM_STOCK_TICKER) String stockTicker, @FormParam(PARAM_PLACE) String place,
			@FormParam(PARAM_MEASURE1) Integer measure1, @FormParam(PARAM_MEASURE2) Integer measure2,
			@FormParam(PARAM_VALUE) String paramValue, @FormParam(PARAM_BINARY_OPERATOR) String bo,@FormParam(PARAM_COMMENT) String comment) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(login, PARAM_LOGIN);
		controllerMessageTools.checkEmptyParameter(stockTicker, PARAM_STOCK_TICKER);
		controllerMessageTools.checkEmptyParameter(login, PARAM_PLACE);
		controllerMessageTools.checkEmptyParameter(measure1, PARAM_MEASURE1);
		controllerMessageTools.checkEmptyParameter(bo, PARAM_BINARY_OPERATOR);

		if (measure2 == null) {
			controllerMessageTools.checkEmptyParameter(paramValue, PARAM_VALUE);
		}

		BigDecimal value = controllerMessageTools.validateBigDecimalParameter(paramValue, PARAM_VALUE);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		BinaryOperatorEnum binaryOperator = BinaryOperatorEnum.valueOf(bo.toUpperCase());

		try {
			return Response.ok(measureService.createMeasureAlert(login, stockTicker, place, measure1, measure2, value,
					binaryOperator, comment), MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), e.getArgs(), context.getLocale()));
			return responseForError.build();
		} catch (BaseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RolesAllowed(RoleConst.USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@GET
	@Path("getAllMeasureAlert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMeasureAlert(@QueryParam(PARAM_LOGIN) String login,
			@QueryParam(PARAM_TRIGGERED) boolean triggered) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(login, PARAM_LOGIN);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			return Response.ok(measureService.getAllMeasure(login, triggered), MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (BaseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RolesAllowed(RoleConst.USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@GET
	@Path("getFile")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFile() {

		File file = new File("C:\\Users\\sauzanne\\Desktop\\essai.xml");
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") // optional
				.build();
	}

}