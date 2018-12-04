
package fr.mystocks.mystockserver.view.controller.account;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
import com.google.common.base.Strings;

import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.service.security.AccountService;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;
import fr.mystocks.mystockserver.view.model.security.AccountCredentialModel;
import fr.mystocks.mystockserver.view.model.security.AccountModel;

@Path("security/account")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PropertiesTools propertiesTools;

	@Inject
	private SpringConfiguration context;

	@RolesAllowed(RoleConst.ADMIN)
	@Application(type = Type.SOFTWARE, os = OS.WIN, name = ApplicationEnum.MYSTOCKS)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("postAuthentication")
	public Response postAuthentication(AccountCredentialModel accountCredentialModel) {

		ResponseBuilder responseForError = Response.status(Status.BAD_REQUEST);

		List<String> errorMessages = new ArrayList<>();
		if (accountCredentialModel == null) {
			errorMessages.add(propertiesTools.getProperty("error.param.least.one"));
		}

		if (!errorMessages.isEmpty()) {
			responseForError.entity(Joiner.on(TechnicalConstant.LINE_SEPARATOR).join(errorMessages));
			return responseForError.build();
		}
		try {
			Account account = null;
			if (!Strings.isNullOrEmpty(accountCredentialModel.getToken())) {
				account = accountService.authenticateWithToken(accountCredentialModel.getToken());

				if (account == null) { // session non reconnue
					return responseForError
							.entity(AccountStateEnum.SESSION_EXPIRED.name())
							.build();

				}

			} else if (!Strings.isNullOrEmpty(accountCredentialModel.getUserName())
					&& !Strings.isNullOrEmpty(accountCredentialModel.getPassword())) {
				account = accountService.authenticate(accountCredentialModel.getUserName(),
						accountCredentialModel.getPassword());

				if (account == null) { // utilisateur non reconnu
					return responseForError
							.entity(AccountStateEnum.BAD_LOGIN_PASSWORD.name())
							.build();

				}
			} else { // ni token, ni user, ni password

				return responseForError.entity(AccountStateEnum.AUTHENTICATION_REQUIRED.name())
						.build();
			}

			AccountModel accountModel = null;
			accountModel = new AccountModel();
			accountModel.convertFormAccount(account);
			return Response.ok(accountModel, MediaType.APPLICATION_JSON).build();
		} catch (FunctionalException e) {
			responseForError.entity(messageSource.getMessage(e.getKeyError(), null, context.getLocale()));
			return responseForError.build();
		} catch (RuntimeException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}