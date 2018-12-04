package fr.mystocks.mystockserver.view.controller.finance;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.service.finance.balancesheets.BalanceSheetsService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;

@Path("finance/balancesheets")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class BalanceSheetsController {
    
	@Autowired
	private BalanceSheetsService balanceSheetsService;

	@Autowired
	private MessageSource messageSource;

	@Inject
	private SpringConfiguration context;

	private final static String PARAM_ID = "id";
	private final static String PARAM_TOKEN = "t";
	private final static String PARAM_ASSETS = "a";
	private final static String PARAM_LIABILITIES = "l";
	private final static String PARAM_EQUITIES = "e";



	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Path("postBalanceSheets")
	public Response postOperations(@FormParam(PARAM_TOKEN) String token, @FormParam(PARAM_ID) Integer id,
			@FormParam(PARAM_ASSETS) Integer assetsId, @FormParam(PARAM_LIABILITIES) Integer liabilitiesId,
			@FormParam(PARAM_EQUITIES) Integer equitiesId,
			@Context SecurityContext securityContext) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());
		controllerMessageTools.checkEmptyParameter(token, PARAM_TOKEN);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			return Response.ok(balanceSheetsService.storeBalanceSheets(token, id, assetsId, liabilitiesId, equitiesId), MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (BaseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}