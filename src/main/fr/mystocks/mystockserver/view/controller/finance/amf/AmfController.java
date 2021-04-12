
package fr.mystocks.mystockserver.view.controller.finance.amf;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
import fr.mystocks.mystockserver.service.finance.amf.AmfService;
import fr.mystocks.mystockserver.service.finance.amf.constant.AmfAddDeleteEnum;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;

@Path("finance/amf")
@Component
public class AmfController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AmfService amfService;

	@Inject
	private SpringConfiguration context;

	private final static String PARAM_LOGIN = "l";
	private final static String PARAM_STOCK_TICKER = "st";

	private static final String PARAM_ADD_DELETE = "ad";

	@RolesAllowed(RoleConst.USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Path("postSubscribeAmfAlert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSubscribeAmfAlert(@FormParam(PARAM_LOGIN) String login,
			@FormParam(PARAM_STOCK_TICKER) String stockTicker,
			@FormParam(PARAM_ADD_DELETE) String ad) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(login, PARAM_LOGIN);
		controllerMessageTools.checkEmptyParameter(stockTicker, PARAM_STOCK_TICKER);
		controllerMessageTools.checkEmptyParameter(ad, PARAM_ADD_DELETE);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		AmfAddDeleteEnum addDelete = AmfAddDeleteEnum.getAmfAddDeleteByValue(ad.toUpperCase());

		try {
			return Response
					.ok(amfService.subscribeAmfAlert(login, stockTicker, addDelete), MediaType.APPLICATION_JSON)
					.build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), e.getArgs(), context.getLocale()));
			return responseForError.build();
		} catch (BaseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}