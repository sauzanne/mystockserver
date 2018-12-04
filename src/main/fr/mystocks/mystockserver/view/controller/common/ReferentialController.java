
package fr.mystocks.mystockserver.view.controller.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.util.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.service.referential.ReferentialService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.BaseException;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;

@Path("common/referential")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class ReferentialController {

	@Autowired
	private ReferentialService referentialService;

	@Autowired
	private MessageSource messageSource;

	@Inject
	private SpringConfiguration context;

	private final static String PARAM_REFERENTIAL_NAME = "rn";

	private final static String GETTER_METHOD = "get";

	@RolesAllowed(RoleConst.ADMIN)
	@GET
	@Path("getAll")
	public Response getFindAll(@QueryParam(PARAM_REFERENTIAL_NAME) String referentialName) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);
		List<String> errorMessages = new ArrayList<>();
		if (Strings.isNullOrEmpty(referentialName)) {
			errorMessages.add(messageSource.getMessage("error.param.empty", new String[] { PARAM_REFERENTIAL_NAME },
					context.getLocale()));
		}

		if (!errorMessages.isEmpty()) {

			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));

			return responseForError.build();
		}
		try {
			JsonArray jsonArray = new JsonArray();

			List<Object> results = referentialService.findAll(StringUtils.uncapitalize(referentialName));

			for (Object obj : results) {
				JsonObject jsonObject = new JsonObject();

				discoverObject(obj, jsonObject);

				jsonArray.add(jsonObject);

			}

			return Response.ok(jsonArray.toString(), MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (BaseException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private void discoverObject(Object obj, JsonObject jsonObject)
			throws IllegalAccessException, InvocationTargetException {
		for (Method method : obj.getClass().getDeclaredMethods()) {
			if (method.getName().startsWith(GETTER_METHOD)) {
				String propertyName = StringUtils.uncapitalize(method.getName().substring(GETTER_METHOD.length()));
				if (TechnicalConstant.isWrapperType(method.getReturnType())) {
					jsonObject.addProperty(propertyName, method.invoke(obj).toString());
				} else {
					JsonObject subObject = new JsonObject();
					discoverObject(method.invoke(obj), subObject);
					jsonObject.add(propertyName, subObject);
				}

			}
		}
	}

}