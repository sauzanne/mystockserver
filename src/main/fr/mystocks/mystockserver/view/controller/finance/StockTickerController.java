
package fr.mystocks.mystockserver.view.controller.finance;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
import com.google.common.base.Strings;

import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.data.security.User;
import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.functional.finance.stock.StockUtils;
import fr.mystocks.mystockserver.service.finance.stockticker.StockTickerService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;
import fr.mystocks.mystockserver.view.model.finance.stockticker.StockTickerModel;

@Path("finance/stockticker")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class StockTickerController {

	@Autowired
	private StockTickerService stockTickerService;

	@Autowired
	private MessageSource messageSource;

	@Inject
	private SpringConfiguration context;

	private final String PARAM_ISIN = "i";
	private final String PARAM_CODE = "c";
	private final String PARAM_PLACE = "p";
	private final String PARAM_MAIN_PLACE = "mp";
	private final String PARAM_BYPASS_PRICE_VERIFICATION = "bpv";

	@RolesAllowed(RoleConst.USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Path("postStockTicker")
	public Response postStock(@FormParam(PARAM_ISIN) String isin, @FormParam(PARAM_CODE) String code,
			@FormParam(PARAM_PLACE) Integer place, @FormParam(PARAM_MAIN_PLACE) String mainPlace,
			@FormParam(PARAM_BYPASS_PRICE_VERIFICATION) String byPassPriceVerification,
			@Context SecurityContext securityContext) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(isin, PARAM_ISIN);
		controllerMessageTools.checkEmptyParameter(code, PARAM_CODE);
		controllerMessageTools.checkEmptyParameter(place, PARAM_PLACE);

		// controllerMessageTools.checkEmptyParameter(myStocksListed,
		// PARAM_MYSTOCKS_LISTED);

		if (!Strings.isNullOrEmpty(isin)) {
			controllerMessageTools
					.addErrorMessages(StockUtils.checkISIN(isin, PARAM_ISIN, messageSource, context.getLocale()));
		}

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			return Response.ok(
					stockTickerService.createStockTicker(isin, code, place, (User) securityContext.getUserPrincipal(),
							Strings.nullToEmpty(mainPlace).equals(TechnicalConstant.CHECKBOX_ON),
							Strings.nullToEmpty(byPassPriceVerification).equals(TechnicalConstant.CHECKBOX_ON)),
					MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (BaseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RolesAllowed(RoleConst.READONLY_USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@GET
	@Path("getStockTicker")
	public Response getStockTicker(@QueryParam(PARAM_CODE) String codeStockTicker,
			@QueryParam(PARAM_PLACE) String placeCode) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();
		if (Strings.isNullOrEmpty(codeStockTicker)) {
			errorMessages.add(
					messageSource.getMessage("error.param.empty", new String[] { PARAM_CODE }, context.getLocale()));
		}

		if (!errorMessages.isEmpty()) {
			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));

			return responseForError.build();
		}

		try {
			StockTicker st = stockTickerService.getStockTicker(codeStockTicker, placeCode);

			StockTickerModel stm = null;

			if (st != null) {
				stm = new StockTickerModel();
				stm.convertFromStockTicker(st, true);
			}

			return Response.ok(stm, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (BaseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}