
package fr.mystocks.mystockserver.view.controller.finance;

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
import fr.mystocks.mystockserver.service.finance.operations.OperationsService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;

@Path("finance/operations")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class OperationsController {

	@Autowired
	private OperationsService operationsService;

	@Autowired
	private MessageSource messageSource;

	@Inject
	private SpringConfiguration context;

	private final static String PARAM_ID = "id";
	private final static String PARAM_TOKEN = "t";
	private final static String PARAM_REVENUES = "r";
	private final static String PARAM_EBIT = "e";
	private final static String PARAM_CURRENT_EBIT = "ce";
	private final static String PARAM_EBITDA = "ea";
	private final static String PARAM_COST_OF_REVENUES = "cor";
	private final static String PARAM_INCOME_TAXES = "it";
	private final static String PARAM_FINANCIAL_EXPENSES = "fe";
	private final static String PARAM_SHAREOWNERS_EARNINGS = "se";
	private final static String PARAM_ADJUSTED_EARNINGS = "ae";
	private final static String PARAM_OPERATIONAL_CASH_FLOW = "ocf";
	private final static String PARAM_FREE_CASH_FLOW = "fcf";
	private final static String PARAM_EXCEPTIONAL_ITEMS = "ei";

	@RolesAllowed(RoleConst.READONLY_USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Path("postOperations")
	public Response postOperations(@FormParam(PARAM_TOKEN) String token, @FormParam(PARAM_ID) Integer id,
			@FormParam(PARAM_REVENUES) String paramRevenues, @FormParam(PARAM_EBIT) String paramEbit,
			@FormParam(PARAM_CURRENT_EBIT) String paramCurrentEbit, @FormParam(PARAM_EBITDA) String paramEbitda,
			@FormParam(PARAM_COST_OF_REVENUES) String paramCostOfRevenues,
			@FormParam(PARAM_FINANCIAL_EXPENSES) String paramFinancialExpenses,
			@FormParam(PARAM_INCOME_TAXES) String paramIncomeTaxes,
			@FormParam(PARAM_SHAREOWNERS_EARNINGS) String paramShareownersEarnings,
			@FormParam(PARAM_ADJUSTED_EARNINGS) String paramAdjustedEarnings,
			@FormParam(PARAM_OPERATIONAL_CASH_FLOW) String paramOperationalCashFlow,
			@FormParam(PARAM_FREE_CASH_FLOW) String paramFreeCashFlow,
			@FormParam(PARAM_EXCEPTIONAL_ITEMS) String paramExceptionalItems,
			@Context SecurityContext securityContext) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		BigDecimal revenues = controllerMessageTools.validateBigDecimalParameter(paramRevenues, PARAM_REVENUES);
		BigDecimal ebit = controllerMessageTools.validateBigDecimalParameter(paramEbit, PARAM_EBIT);
		BigDecimal currentEbit = controllerMessageTools.validateBigDecimalParameter(paramCurrentEbit,
				PARAM_CURRENT_EBIT);
		BigDecimal costOfRevenues = controllerMessageTools.validateBigDecimalParameter(paramCostOfRevenues,
				PARAM_COST_OF_REVENUES);
		BigDecimal financialExpenses = controllerMessageTools.validateBigDecimalParameter(paramFinancialExpenses,
				PARAM_FINANCIAL_EXPENSES);
		
		BigDecimal incomeTaxes = controllerMessageTools.validateBigDecimalParameter(paramIncomeTaxes,
				PARAM_INCOME_TAXES);

		BigDecimal shareownersEarnings = controllerMessageTools.validateBigDecimalParameter(paramShareownersEarnings,
				PARAM_SHAREOWNERS_EARNINGS);
		BigDecimal adjustedEarnings = controllerMessageTools.validateBigDecimalParameter(paramAdjustedEarnings,
				PARAM_ADJUSTED_EARNINGS);

		BigDecimal operationalCashFlow = controllerMessageTools.validateBigDecimalParameter(paramOperationalCashFlow,
				PARAM_OPERATIONAL_CASH_FLOW);
		BigDecimal freeCashFlow = controllerMessageTools.validateBigDecimalParameter(paramFreeCashFlow,
				PARAM_FREE_CASH_FLOW);
		BigDecimal exceptionalItems = controllerMessageTools.validateBigDecimalParameter(paramExceptionalItems,
				PARAM_EXCEPTIONAL_ITEMS);

		BigDecimal ebitda = controllerMessageTools.validateBigDecimalParameter(paramEbitda, PARAM_EBITDA);

		controllerMessageTools.checkEmptyParameter(token, PARAM_TOKEN);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			return Response.ok(operationsService.storeOperations(token, id, revenues, ebit, currentEbit, ebitda,
					costOfRevenues, financialExpenses, incomeTaxes, shareownersEarnings, adjustedEarnings, operationalCashFlow,
					freeCashFlow, exceptionalItems), MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (BaseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}