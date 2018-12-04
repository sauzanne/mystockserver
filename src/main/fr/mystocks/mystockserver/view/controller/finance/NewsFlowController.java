
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

import fr.mystocks.mystockserver.data.finance.newsflow.NewsFlow;
import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.service.finance.newsflow.NewsFlowService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.ControllerMessageTools;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;
import fr.mystocks.mystockserver.view.model.finance.newsflow.NewsFlowModel;

@Path("finance/newsflow")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class NewsFlowController {

	@Autowired
	private NewsFlowService newsFlowService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PropertiesTools propertiesTools;

	@Inject
	private SpringConfiguration context;

	private static final String PARAM_ACCOUNT_TOKEN = "t";
	private static final String PARAM_ID = "id";
	private static final String PARAM_NAME = "n";
	private static final String PARAM_URL = "u";
	private static final String PARAM_KEYWORDS = "k";
	private static final String PARAM_NOTIFICATION = "nt";

	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@GET
	@Path("getNewsFlow")
	public Response getListStock(@QueryParam(PARAM_ACCOUNT_TOKEN) String token) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();
		if (Strings.isNullOrEmpty(token)) {
			errorMessages.add(propertiesTools.getProperty("error.param.least.one"));
		}

		if (!errorMessages.isEmpty()) {
			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));

			return responseForError.build();
		}

		try {
			List<NewsFlow> listNewsFlow = newsFlowService.getNewsFlowByToken(token);

			List<NewsFlowModel> listNewsFlowModel = new ArrayList<>();
			if (listNewsFlow != null) {
				for (NewsFlow nf : listNewsFlow) {
					NewsFlowModel nfm = new NewsFlowModel();
					nfm.convertFromNewsFlow(nf);
					listNewsFlowModel.add(nfm);
				}
			}
			return Response.ok(listNewsFlowModel, MediaType.APPLICATION_JSON).build();
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
	@Path("postNewsFlow")
	public Response postNewsFlow(@FormParam(PARAM_ACCOUNT_TOKEN) String token, @FormParam(PARAM_ID) Integer id,
			@FormParam(PARAM_NAME) String name, @FormParam(PARAM_URL) String url,
			@FormParam(PARAM_KEYWORDS) String keywords, @FormParam(PARAM_NOTIFICATION) String notification,
			@Context SecurityContext securityContext) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		ControllerMessageTools controllerMessageTools = new ControllerMessageTools(messageSource, context.getLocale());

		controllerMessageTools.checkEmptyParameter(token, PARAM_ACCOUNT_TOKEN);
		controllerMessageTools.checkEmptyParameter(name, PARAM_NAME);
		controllerMessageTools.checkEmptyParameter(url, PARAM_URL);
		controllerMessageTools.checkEmptyParameter(keywords, PARAM_KEYWORDS);

		if (!controllerMessageTools.getErrorMessages().isEmpty()) {
			responseForError.entity(
					Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(controllerMessageTools.getErrorMessages()));

			return responseForError.build();
		}

		try {
			return Response.ok(newsFlowService.createNewsFlow(token, id, name, url, keywords, Strings.nullToEmpty(notification).equals(TechnicalConstant.CHECKBOX_ON))).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (BaseException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}