package fr.mystocks.mystockserver.view.controller.security;

import java.security.NoSuchAlgorithmException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.mystocks.mystockserver.data.security.User;
import fr.mystocks.mystockserver.service.security.SecurityService;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.view.model.security.CredentialModel;

@Path("authentication")
@Component
public class AuthenticationEndPoint {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityService securityService;

    @POST
    @Consumes("application/json")
    public Response authenticateUser(CredentialModel credential) {

	// Authenticate the user using the credentials provided
	try {
	    User user = authenticate(credential.getUserName(), credential.getPassword());
	    if (user == null) {
		return Response.status(Response.Status.UNAUTHORIZED).build();
	    }

	    // Issue a token for the user
	    String token = issueToken(user);

	    // Return the token on the response
	    return Response.ok(token).build();

	} catch (NoSuchAlgorithmException e) {
	    ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
	    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	} catch (RuntimeException e) {
	    ExceptionTools.processExceptionOnlyWithLogging(this, logger, e);
	    return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();

	}
    }

    private User authenticate(String username, String password) throws NoSuchAlgorithmException {
	// Authenticate against a database, LDAP, file or whatever
	// Throw an Exception if the credentials are invalid
	return securityService.authenticate(username, password);

    }

    private String issueToken(User user) {
	// Issue a token (can be a random String persisted to a database or a
	// JWT token)
	// The issued token must be associated to a user
	// Return the issued token
	return securityService.getToken(user);
    }
}