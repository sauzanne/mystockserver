
package fr.mystocks.mystockserver.view.controller.finance.assets;

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
import fr.mystocks.mystockserver.service.finance.assets.AssetsService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;

@Path("finance/assets")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class AssetsController {

	@Autowired
	private AssetsService assetsService;

	@Autowired
	private MessageSource messageSource;

	@Inject
	private SpringConfiguration context;

	private final static String PARAM_ID = "id";
	private final static String PARAM_TOKEN = "t";
	private final static String PARAM_CASH = "c";
	private final static String PARAM_INVENTORIES = "i";
	private final static String PARAM_CURRENT_ASSETS = "ca";
	private final static String PARAM_GOODWILL = "gw";
	private final static String PARAM_TRADE_ACCOUNTS = "ta";

	@RolesAllowed(RoleConst.READONLY_USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Path("postAssets")
	public Response postOperations(@FormParam(PARAM_TOKEN) String token, @FormParam(PARAM_ID) Integer id,
			@FormParam(PARAM_CASH) String paramCash, @FormParam(PARAM_INVENTORIES) String paramInventories,
			@FormParam(PARAM_CURRENT_ASSETS) String paramCurrentAssets, @FormParam(PARAM_GOODWILL) String paramGoodWill,
			@FormParam(PARAM_TRADE_ACCOUNTS) String paramTradeAccounts, @Context SecurityContext securityContext) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		BigDecimal cash = controllerMessageTools.validateBigDecimalParameter(paramCash, PARAM_CASH);
		BigDecimal inventories = controllerMessageTools.validateBigDecimalParameter(paramInventories,
				PARAM_INVENTORIES);
		BigDecimal currentAssets = controllerMessageTools.validateBigDecimalParameter(paramCurrentAssets,
				PARAM_CURRENT_ASSETS);

		BigDecimal goodwill = controllerMessageTools.validateBigDecimalParameter(paramGoodWill, PARAM_GOODWILL);

		BigDecimal tradeAccounts = controllerMessageTools.validateBigDecimalParameter(paramTradeAccounts,
				PARAM_TRADE_ACCOUNTS);

		controllerMessageTools.checkEmptyParameter(token, PARAM_TOKEN);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			return Response
					.ok(assetsService.storeAssets(token, id, cash, inventories, currentAssets, goodwill, tradeAccounts),
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