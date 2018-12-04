
package fr.mystocks.mystockserver.view.controller.finance;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

import fr.mystocks.mystockserver.data.finance.liststock.ListStock;
import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.service.finance.liststock.ListStockService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;
import fr.mystocks.mystockserver.view.model.finance.liststock.ListStockModel;
import fr.mystocks.mystockserver.view.model.finance.liststockelement.DeleteElementsModel;

@Path("finance/liststock")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class ListStockController {

	@Autowired
	private ListStockService listStockService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PropertiesTools propertiesTools;

	@Inject
	private SpringConfiguration context;

	private static final String PARAM_ACCOUNT_LOGIN = "u";
	private static final String PARAM_STOCK = "s";
	private static final String PARAM_LIST_STOCK = "l";
	private static final String PARAM_LIST_NAME = "n";
	private static final String PARAM_ACCOUNT_ID = "i";
	private static final String PARAM_LIST_ID = "id";


	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@GET
	@Path("getLists")
	public Response getListStock(@QueryParam(PARAM_ACCOUNT_LOGIN) String user,
			@QueryParam(PARAM_LIST_STOCK) Integer listStockId) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();
		if (Strings.isNullOrEmpty(user)) {
			errorMessages.add(propertiesTools.getProperty("error.param.least.one"));
		}

		if (!errorMessages.isEmpty()) {
			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));

			return responseForError.build();
		}

		try {
			List<ListStock> listStocks = listStockService.getListStockByUser(user, listStockId);

			List<ListStockModel> listStockModel = new ArrayList<>();
			if (listStocks != null) {
				for (ListStock ls : listStocks) {
					ListStockModel lsm = new ListStockModel();
					lsm.convertFromListStock(ls);
					listStockModel.add(lsm);
				}
			}
			return Response.ok(listStockModel, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (RuntimeException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@PUT
	@Path("putElement")
	public Response putListStock(@FormParam(PARAM_STOCK) Integer stock,
			@FormParam(PARAM_LIST_STOCK) Integer listStock) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(stock, PARAM_STOCK);
		controllerMessageTools.checkEmptyParameter(listStock, PARAM_LIST_STOCK);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			Integer stockElementId = listStockService.createElementInList(stock, listStock);
			return Response.ok(stockElementId, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (RuntimeException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@PUT
	@Path("putList")
	public Response putListStock(@FormParam(PARAM_LIST_NAME) String listName,
			@FormParam(PARAM_ACCOUNT_ID) Integer accountId) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(listName, PARAM_LIST_NAME);
		controllerMessageTools.checkEmptyParameter(accountId, PARAM_ACCOUNT_ID);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			Integer listStockId = listStockService.createListStock(listName, accountId);
			return Response.ok(listStockId, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (RuntimeException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@DELETE
	@Path("deleteList")
	public Response deleteListStock(@QueryParam(PARAM_LIST_ID) Integer id) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(id, PARAM_LIST_ID);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			Integer numberOfDeletedElements = listStockService.deleteList(id);
			return Response.ok(numberOfDeletedElements, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (RuntimeException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}


	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("postRetryElements")
	public Response deleteElements(DeleteElementsModel deleteElementsModel) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(deleteElementsModel.getLogin(), "login");
		controllerMessageTools.checkEmptyParameter(deleteElementsModel.getListToDelete(), "listToDelete");

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			Integer numberOfDeletedElements = listStockService.deleteElements(deleteElementsModel.getLogin(),
					deleteElementsModel.getListToDelete());
			return Response.ok(numberOfDeletedElements, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (RuntimeException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}