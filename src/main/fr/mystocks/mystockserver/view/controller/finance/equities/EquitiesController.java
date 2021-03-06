
package fr.mystocks.mystockserver.view.controller.finance.equities;

import java.math.BigDecimal;

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
import fr.mystocks.mystockserver.service.finance.equities.EquitiesService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;

@Path("finance/equities")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class EquitiesController {

	@Autowired
	private EquitiesService equitiesService;

	@Autowired
	private MessageSource messageSource;

	@Inject
	private SpringConfiguration context;

	private final static String PARAM_ID = "id";
	private final static String PARAM_TOKEN = "t";
	private final static String PARAM_SHAREHOLDER_EQUITY = "se";
	private final static String PARAM_NON_CONTROLLING_INTERESTS = "nci";


	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Path("postEquities")
	public Response postOperations(@FormParam(PARAM_TOKEN) String token, @FormParam(PARAM_ID) Integer id,
			@FormParam(PARAM_SHAREHOLDER_EQUITY) String paramShareholderEquity,@FormParam(PARAM_NON_CONTROLLING_INTERESTS) String paramNonControllingInterests, @Context SecurityContext securityContext) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		BigDecimal shareholderEquity = controllerMessageTools.validateBigDecimalParameter(paramShareholderEquity, PARAM_SHAREHOLDER_EQUITY);
		BigDecimal nonControllingInterests = controllerMessageTools.validateBigDecimalParameter(paramNonControllingInterests, PARAM_NON_CONTROLLING_INTERESTS);


		controllerMessageTools.checkEmptyParameter(token, PARAM_TOKEN);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			return Response
					.ok(equitiesService.storeEquities(token, id, shareholderEquity, nonControllingInterests),
							MediaType.APPLICATION_JSON)
					.build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (BaseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}