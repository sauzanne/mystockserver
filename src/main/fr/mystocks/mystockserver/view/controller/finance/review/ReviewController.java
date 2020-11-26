
package fr.mystocks.mystockserver.view.controller.finance.review;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import fr.mystocks.mystockserver.data.finance.review.Review;
import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.service.finance.constant.PeriodEnum;
import fr.mystocks.mystockserver.service.finance.review.ReviewService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.date.DateTools;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;
import fr.mystocks.mystockserver.view.model.finance.review.ReviewModel;

@Path("finance/review")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private MessageSource messageSource;

	@Inject
	private SpringConfiguration context;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final static String PARAM_STOCK = "s";
	private final static String PARAM_START_YEAR = "sy";
	private final static String PARAM_END_YEAR = "ey";
	private final static String PARAM_PERIOD = "p";
	private final static String PARAM_TOKEN = "t";
	private final static String PARAM_START_DATE = "sd";
	private final static String PARAM_OPERATIONS = "o";
	private final static String PARAM_BALANCE_SHEETS = "bs";
	private final static String PARAM_CURRENCY_ID = "c";
	private final static String PARAM_PUBLICATION_DATE = "pd";
	private final static String PARAM_NB_SHARES_END_PERIOD = "ns";
	private final static String PARAM_FREE_FLOAT = "ff";
	private final static String PARAM_VALUATION_ID = "v";


	private final static String PARAM_ID = "id";

	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@GET
	@Path("getReviews")
	public Response getReview(@QueryParam(PARAM_STOCK) Integer stockId, @QueryParam(PARAM_TOKEN) String token,
			@QueryParam(PARAM_START_YEAR) Integer startYear, @QueryParam(PARAM_END_YEAR) Integer endYear,
			@QueryParam(PARAM_PERIOD) String period) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());
		try {

			controllerMessageTools.checkEmptyParameter(stockId, PARAM_STOCK);
			controllerMessageTools.checkEmptyParameter(token, PARAM_TOKEN);

			if (!controllerMessageTools.getErrorMessages().isEmpty()) {
				responseForError.entity(
						Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

				return responseForError.build();
			}

			PeriodEnum periodEnum = null;
			if (!Strings.isNullOrEmpty(period)) {
				periodEnum = PeriodEnum.valueOf(Strings.nullToEmpty(period));
			}

			List<Review> listReview = reviewService.findReview(stockId, token, startYear, endYear, periodEnum);

			List<ReviewModel> listReviewModel = new ArrayList<>();
			if (listReview != null) {
				for (Review r : listReview) {
					ReviewModel rm = new ReviewModel();
					rm.convertFromReview(r);
					listReviewModel.add(rm);
				}
			}
			return Response.ok(listReviewModel, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (RuntimeException e) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Path("postReview")
	public Response postNewsFlow(@FormParam(PARAM_TOKEN) String token, @FormParam(PARAM_ID) Integer id,
			@FormParam(PARAM_STOCK) Integer stockId, @FormParam(PARAM_PERIOD) String period,
			@FormParam(PARAM_START_YEAR) Integer startYear, @FormParam(PARAM_END_YEAR) Integer endYear,
			@FormParam(PARAM_START_DATE) String startDate, @FormParam(PARAM_PUBLICATION_DATE) String publicationDate,
			@FormParam(PARAM_NB_SHARES_END_PERIOD) String paramNbSharesEndPeriod,
			@FormParam(PARAM_FREE_FLOAT) String paramFreeFloat, @FormParam(PARAM_OPERATIONS) Integer operationsId,
			@FormParam(PARAM_BALANCE_SHEETS) Integer balanceSheetsId, @FormParam(PARAM_CURRENCY_ID) Integer currencyId,
			@FormParam(PARAM_VALUATION_ID) Integer valuationId,
			@Context SecurityContext securityContext) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());
		try {

			controllerMessageTools.checkEmptyParameter(token, PARAM_TOKEN);
			controllerMessageTools.checkEmptyParameter(stockId, PARAM_STOCK);
			controllerMessageTools.checkEmptyParameter(startYear, PARAM_START_YEAR);
			controllerMessageTools.checkEmptyParameter(period, PARAM_PERIOD);
			controllerMessageTools.checkEmptyParameter(startDate, PARAM_START_DATE);
			controllerMessageTools.checkEmptyParameter(currencyId, PARAM_CURRENCY_ID);

			BigInteger nbSharesEndPeriod = controllerMessageTools.validateBigIntegerParameter(paramNbSharesEndPeriod,
					PARAM_NB_SHARES_END_PERIOD);

			Double freeFloat = controllerMessageTools.validateDoubleParameter(paramFreeFloat, PARAM_FREE_FLOAT);

			controllerMessageTools.validateDateFormatParameter(startDate, DateTimeFormatter.ISO_LOCAL_DATE,
					PARAM_START_DATE);

			controllerMessageTools.validateDateFormatParameter(publicationDate, DateTimeFormatter.ISO_LOCAL_DATE,
					PARAM_PUBLICATION_DATE);

			controllerMessageTools.checkValue1SuperiorOrEqualsValue2(endYear, startYear, PARAM_END_YEAR,
					PARAM_START_YEAR);

			PeriodEnum periodEnum = null;
			if (!Strings.isNullOrEmpty(period)) {
				try {
					periodEnum = PeriodEnum.valueOf(Strings.nullToEmpty(period));
				} catch (RuntimeException e) {
					logger.warn(e.getMessage());
					List<String> messageToAdd = new ArrayList<>();
					messageToAdd.add(messageSource.getMessage("error.format.bad", new String[] { PARAM_PERIOD },
							context.getLocale()));
					controllerMessageTools.addErrorMessages(messageToAdd);
				}
			}

			if (!controllerMessageTools.getErrorMessages().isEmpty()) {
				responseForError.entity(
						Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

				return responseForError.build();
			}

			LocalDate localStartDate = DateTools.convertIsoLocalDate(startDate);
			LocalDate localPublicationDate = null;
			if (publicationDate != null)
				localPublicationDate = DateTools.convertIsoLocalDate(publicationDate);

			return Response.ok(reviewService.createUpdateReview(token, id, stockId, periodEnum, startYear, endYear,
					localStartDate, localPublicationDate, nbSharesEndPeriod, freeFloat, operationsId, balanceSheetsId,
					currencyId, valuationId)).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (RuntimeException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}