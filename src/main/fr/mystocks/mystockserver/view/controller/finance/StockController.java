
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

import fr.mystocks.mystockserver.data.finance.stock.Stock;
import fr.mystocks.mystockserver.data.security.User;
import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.functional.finance.stock.StockUtils;
import fr.mystocks.mystockserver.service.finance.stock.StockService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;
import fr.mystocks.mystockserver.view.model.finance.stock.StockModel;

@Path("finance/stock")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class StockController {

	@Autowired
	private StockService stockService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PropertiesTools propertiesTools;

	@Inject
	private SpringConfiguration context;

	private final String PARAM_ID = "id";
	private final String PARAM_ISIN = "i";
	private final String PARAM_NAME = "n";
	private final String PARAM_STOCK_TYPE = "st";
	private final String PARAM_MYSTOCKS_LISTED = "msl";

	@RolesAllowed(RoleConst.READONLY_USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Path("postStock")
	public Response postStock(@FormParam(PARAM_ISIN) String isin, @FormParam(PARAM_NAME) String name,
			@FormParam(PARAM_STOCK_TYPE) Integer stockType, @FormParam(PARAM_MYSTOCKS_LISTED) String myStocksListed,
			@Context SecurityContext securityContext) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(isin, PARAM_ISIN);
		controllerMessageTools.checkEmptyParameter(name, PARAM_NAME);
		controllerMessageTools.checkEmptyParameter(stockType, PARAM_STOCK_TYPE);
		// controllerMessageTools.checkEmptyParameter(myStocksListed,
		// PARAM_MYSTOCKS_LISTED);

		controllerMessageTools
				.addErrorMessages(StockUtils.checkISIN(isin, PARAM_ISIN, messageSource, context.getLocale()));

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			return Response.ok(
					stockService.storeStock(isin, name, stockType, (User) securityContext.getUserPrincipal(),
							Strings.nullToEmpty(myStocksListed).equals(TechnicalConstant.CHECKBOX_ON), false),
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
	@Path("findStocks")
	public Response findStocks(@QueryParam(PARAM_ID) Integer id, @QueryParam(PARAM_ISIN) String isin,
			@QueryParam(PARAM_NAME) String name) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();
		if (Strings.isNullOrEmpty(isin) && id == null && Strings.isNullOrEmpty(name)) {
			errorMessages.add(propertiesTools.getProperty("error.param.least.one"));
		}

		if (!errorMessages.isEmpty()) {
			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));

			return responseForError.build();
		}

		try {
			List<Stock> listStocks = stockService.findStocks(id, name, isin);
			List<StockModel> listStockModel = new ArrayList<>();

			for (Stock s : listStocks) {
				StockModel stockModel = new StockModel();

				stockModel.convertFromStock(s, true);
				listStockModel.add(stockModel);
			}
			return Response.ok(listStockModel, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (RuntimeException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}