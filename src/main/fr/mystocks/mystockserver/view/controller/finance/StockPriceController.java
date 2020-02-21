
package fr.mystocks.mystockserver.view.controller.finance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
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

import fr.mystocks.mystockserver.data.finance.place.Place;
import fr.mystocks.mystockserver.data.finance.stockprice.StockPrice;
import fr.mystocks.mystockserver.data.finance.stockticker.StockTicker;
import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.service.finance.stockprice.StockPriceService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;
import fr.mystocks.mystockserver.view.model.finance.stockprice.StockPriceModel;

@Path("finance/stockprice")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class StockPriceController {

	@Autowired
	private StockPriceService stockPriceService;

	@Autowired
	private MessageSource messageSource;

	@Inject
	private SpringConfiguration context;

	private final String PARAM_PERIOD_START = "ps";
	private final String PARAM_PERIOD_END = "pe";
	private final String PARAM_STOCK_TICKER = "s";
	private final String PARAM_PLACE = "p";

	@RolesAllowed(RoleConst.READONLY_USER)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@GET
	@Path("getLast")
	public Response getLast(@QueryParam(PARAM_STOCK_TICKER) String stockTicker,
			@QueryParam(PARAM_PLACE) String placeCode) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();
		if (Strings.isNullOrEmpty(stockTicker)) {
			errorMessages.add(messageSource.getMessage("error.param.empty", new String[] { PARAM_STOCK_TICKER },
					context.getLocale()));
		}
		if (Strings.isNullOrEmpty(placeCode)) {
			errorMessages.add(
					messageSource.getMessage("error.param.empty", new String[] { PARAM_PLACE }, context.getLocale()));
		}

		if (!errorMessages.isEmpty()) {
			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));

			return responseForError.build();
		}

		try {
			StockTicker st = new StockTicker();
			st.setCode(stockTicker);
			Place p = new Place();
			p.setCode(placeCode);
			st.setPlace(p);
			StockPrice sp = stockPriceService.getLast(st);
			StockPriceModel spm = null;
			if (sp != null) {
				spm = new StockPriceModel();
				spm.convertFromStockPrice(sp);
			}
			return Response.ok(spm, MediaType.APPLICATION_JSON).build();
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
	@Path("getLastForList")
	public Response getLastForList(@QueryParam(PARAM_STOCK_TICKER) String stockTickers,
			@QueryParam(PARAM_PLACE) String placeCode) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();
		if (Strings.isNullOrEmpty(stockTickers)) {
			errorMessages.add(messageSource.getMessage("error.param.empty", new String[] { PARAM_STOCK_TICKER },
					context.getLocale()));
		}
		if (Strings.isNullOrEmpty(placeCode)) {
			errorMessages.add(
					messageSource.getMessage("error.param.empty", new String[] { PARAM_PLACE }, context.getLocale()));
		}

		if (!errorMessages.isEmpty()) {
			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));

			return responseForError.build();
		}

		try {
			String[] listOfTickers = stockTickers.split(",");
			List<StockTicker> listOfStockTickers = new ArrayList<>();

			Place p = new Place();
			p.setCode(placeCode);

			for (String stockTicker : listOfTickers) {
				StockTicker st = new StockTicker();
				st.setCode(stockTicker.trim());
				st.setPlace(p);
				listOfStockTickers.add(st);
			}
			List<StockPriceModel> lspm = stockPriceService.getLastForList(listOfStockTickers).stream()
					.map(lsp -> new StockPriceModel(lsp.getPrice(), lsp.getStockPriceId().getInputDate(),
							lsp.getClose(), lsp.getStockPriceId().getStockTicker()))
					.collect(Collectors.toList());
			return Response.ok(lspm, MediaType.APPLICATION_JSON).build();
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
	@Path("getPriceForPeriod")
	public Response getPriceForPeriod(@QueryParam(PARAM_STOCK_TICKER) String stockTicker,
			@QueryParam(PARAM_PLACE) String placeCode, @QueryParam(PARAM_PERIOD_START) String start,
			@QueryParam(PARAM_PERIOD_END) String end) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();
		LocalDate startDate = null;
		LocalDate endDate = null;

		if (Strings.isNullOrEmpty(stockTicker)) {
			errorMessages.add(messageSource.getMessage("error.param.empty", new String[] { PARAM_STOCK_TICKER },
					context.getLocale()));
		}
		if (Strings.isNullOrEmpty(placeCode)) {
			errorMessages.add(
					messageSource.getMessage("error.param.empty", new String[] { PARAM_PLACE }, context.getLocale()));
		}

		if (Strings.isNullOrEmpty(start)) {
			errorMessages.add(messageSource.getMessage("error.finance.stockprice.period.param.start",
					new String[] { PARAM_PERIOD_START }, context.getLocale()));
		} else {
			try {
				startDate = LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE);
			} catch (DateTimeParseException e) {
				errorMessages.add(messageSource.getMessage("error.param.date.format",
						new String[] { PARAM_PERIOD_START }, context.getLocale()));
			}

		}

		if (startDate != null) {
			if (!Strings.isNullOrEmpty(end)) {
				try {
					endDate = LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE);
				} catch (DateTimeParseException e) {
					errorMessages.add(messageSource.getMessage("error.param.date.format",
							new String[] { PARAM_PERIOD_END }, context.getLocale()));
				}
			}

			if (endDate != null && endDate.isBefore(startDate)) {
				errorMessages.add(messageSource.getMessage("error.param.date.before",
						new String[] { PARAM_PERIOD_END, PARAM_PERIOD_START }, context.getLocale()));

			} else if (endDate == null && startDate.isAfter(LocalDate.now())) {
				errorMessages.add(messageSource.getMessage("error.param.date.before.current",
						new String[] { PARAM_PERIOD_END }, context.getLocale()));
			}
		}

		if (!errorMessages.isEmpty()) {
			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));

			return responseForError.build();
		}

		try {
			StockTicker st = new StockTicker();
			st.setCode(stockTicker);
			Place p = new Place();
			p.setCode(placeCode);
			st.setPlace(p);
			List<StockPrice> listStockPrice = stockPriceService.getPriceForPeriod(st, startDate,
					endDate == null ? LocalDate.now() : endDate);

			List<StockPriceModel> result = new ArrayList<>();
			for (StockPrice sp : listStockPrice) {
				StockPriceModel spm = new StockPriceModel();
				spm.convertFromStockPrice(sp);
				result.add(spm);
			}
			return Response.ok(result, MediaType.APPLICATION_JSON).build();
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
	@Path("getPreviousForList")
	public Response getPreviousForList(@QueryParam(PARAM_STOCK_TICKER) String stockTickers,
			@QueryParam(PARAM_PLACE) String placeCode) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();

		if (Strings.isNullOrEmpty(stockTickers)) {
			errorMessages.add(messageSource.getMessage("error.param.empty", new String[] { PARAM_STOCK_TICKER },
					context.getLocale()));
		}
		if (Strings.isNullOrEmpty(placeCode)) {
			errorMessages.add(
					messageSource.getMessage("error.param.empty", new String[] { PARAM_PLACE }, context.getLocale()));
		}


		if (!errorMessages.isEmpty()) {
			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));

			return responseForError.build();
		}

		try {
			
			String[] listOfTickers = stockTickers.split(",");
			List<StockTicker> listOfStockTickers = new ArrayList<>();

			Place p = new Place();
			p.setCode(placeCode);

			for (String stockTicker : listOfTickers) {
				StockTicker st = new StockTicker();
				st.setCode(stockTicker.trim());
				st.setPlace(p);
				listOfStockTickers.add(st);
			}

			List<StockPrice> listStockPrice = stockPriceService.getPreviousForList(listOfStockTickers);

			List<StockPriceModel> result = new ArrayList<>();
			for (StockPrice sp : listStockPrice) {
				StockPriceModel spm = new StockPriceModel();
				spm.convertFromStockPrice(sp);
				result.add(spm);
			}
			return Response.ok(result, MediaType.APPLICATION_JSON).build();
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
	@Path("getAveragePrice")
	public Response getAveragePrice(@QueryParam(PARAM_STOCK_TICKER) String stockTicker,
			@QueryParam(PARAM_PLACE) String placeCode, @QueryParam(PARAM_PERIOD_START) String start,
			@QueryParam(PARAM_PERIOD_END) String end) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		LocalDate startDate = null;
		LocalDate endDate = null;

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(stockTicker, PARAM_STOCK_TICKER);
		controllerMessageTools.checkEmptyParameter(placeCode, PARAM_PLACE);

		if (Strings.isNullOrEmpty(start)) {
			controllerMessageTools.checkEmptyParameter(start, PARAM_PERIOD_START);
		} else {
			startDate = controllerMessageTools.validateDateFormatParameter(start, DateTimeFormatter.ISO_LOCAL_DATE,
					PARAM_PERIOD_START);
		}

		if (startDate != null) {
			endDate = controllerMessageTools.validateDateFormatParameter(end, DateTimeFormatter.ISO_LOCAL_DATE,
					PARAM_PERIOD_END);

			controllerMessageTools.checkDateBefore(startDate, endDate, PARAM_PERIOD_START, PARAM_PERIOD_END);

			if (endDate == null) {
				controllerMessageTools.checkDateBeforeNow(startDate, PARAM_PERIOD_START);
			}
		}

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			StockTicker st = new StockTicker();
			st.setCode(stockTicker);
			Place p = new Place();
			p.setCode(placeCode);
			st.setPlace(p);
			BigDecimal averagePrice = stockPriceService.getAveragePrice(st, startDate,
					endDate == null ? LocalDate.now() : endDate);

			return Response.ok(averagePrice.doubleValue(), MediaType.APPLICATION_JSON).build();
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
	@Path("getCheckStock")
	public Response checkStock(@QueryParam(PARAM_STOCK_TICKER) String stockTicker,
			@QueryParam(PARAM_PLACE) String placeCode) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(stockTicker, PARAM_STOCK_TICKER);

		controllerMessageTools.checkEmptyParameter(stockTicker, PARAM_PLACE);

		if (!errorMessages.isEmpty()) {
			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));

			return responseForError.build();
		}

		try {
			StockTicker st = new StockTicker();
			st.setCode(stockTicker);
			Place p = new Place();
			p.setCode(placeCode);
			st.setPlace(p);
			Boolean stockTickerOk = stockPriceService.checkStocks(st);

			return Response.ok(stockTickerOk, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (BaseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}