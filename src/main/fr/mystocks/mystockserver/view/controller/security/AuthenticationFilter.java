package fr.mystocks.mystockserver.view.controller.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import fr.mystocks.mystockserver.data.security.MystockServerSecurityContext;
import fr.mystocks.mystockserver.data.security.Session;
import fr.mystocks.mystockserver.data.security.constant.RoleEnum;
import fr.mystocks.mystockserver.service.security.SecurityService;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;
import fr.mystocks.mystockserver.technic.security.annotation.Secured;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
@Component
public class AuthenticationFilter implements ContainerRequestFilter, ApplicationContextAware {

    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Context
    private ResourceInfo resourceInfo;

    @Autowired
    private MessageSource messageSource;

    @Inject
    //private SpringConfiguration context;

    private ApplicationContext applicationContext;

    private static final ResponseBuilder ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN); // 403
    private static final ResponseBuilder ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED); // 401
    private static final ResponseBuilder INTERNAL_SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR); // 500
    private static final String AUTHENTICATION = "authentication";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

	// Get the Authorization header from the request
	String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
	
        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        InputStream in = requestContext.getEntityStream();
//        final StringBuilder b = new StringBuilder();
//        try {
//            if (in.available() > 0) {
          //      ReaderWriter.writeTo(in, out);
//
//                byte[] requestEntity = out.toByteArray();
//                b.append(new String(requestEntity)).append("\n");
//                System.out.println("#### Intercepted Entity ####");
//                System.out.println(b.toString());
//            }
//        } catch (IOException ex) {
//            throw new ContainerException(ex);
//        }


	/* si l'URL contient authentication, on ne filtre pas */
	if (requestContext.getUriInfo().getAbsolutePath().toString().contains(AUTHENTICATION)) {
	    return;
	}

	// Validate the Authorization header
	if (!isTokenBasedAuthentication(authorizationHeader)) {
	    abortWithUnauthorized(requestContext);
	    return;
	}

	// Extract the token from the Authorization header
	String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

	try {

	    // Validate the token
	    validateToken(token, requestContext);

	} catch (Exception e) {
	    abortWithInternalServerError(requestContext);
	}
    }

    private void abortWithInternalServerError(ContainerRequestContext requestContext) {
	requestContext
		.abortWith(INTERNAL_SERVER_ERROR.header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME).build());

    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

	// Check if the Authorization header is valid
	// It must not be null and must be prefixed with "Bearer" plus a
	// whitespace
	// Authentication scheme comparison must be case-insensitive
	return authorizationHeader != null
		&& authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

	// Abort the filter chain with a 401 status code
	// The "WWW-Authenticate" is sent along with the response
	requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
		.header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME).build());
    }

    private void validateToken(String token, ContainerRequestContext requestContext) throws Exception {
	Method method = resourceInfo.getResourceMethod();
	// Access allowed for all
	if (!method.isAnnotationPresent(PermitAll.class)) {
	    // Access denied for all
	    if (method.isAnnotationPresent(DenyAll.class)) {
		requestContext.abortWith(ACCESS_FORBIDDEN.entity(
			messageSource.getMessage("error.authentication.access.blocked.all", null, Locale.ENGLISH))
			.build());
		return;
	    }

	    Session session = null;

	    // Verify user access
	    if (method.isAnnotationPresent(RolesAllowed.class)) {
		RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
		List<String> rolesSet = Arrays.asList(rolesAnnotation.value());

		if (!rolesSet.isEmpty()) {
		    List<RoleEnum> rolesAllowed = RoleEnum.getRoleFromLevel(RoleEnum.valueOf(rolesSet.get(0)));

		    // if we don't get a session we get out of the method
		    if ((session = getSessionAndCheckUserAllowed(token, rolesAllowed, requestContext)) == null) {
			return;
		    }
		    //we set the security context
		    requestContext.setSecurityContext(new MystockServerSecurityContext(session.getUser(), token));
		}
	    }

	    // we check if the user is allowed for a specific application
	    if (method.isAnnotationPresent(Application.class) && session != null) {
		if (!isUserAllowedForApplication(method, session)) {
		    requestContext.abortWith(ACCESS_FORBIDDEN.entity(messageSource
			    .getMessage("error.authentication.access.forbidden", null, Locale.ENGLISH)).build());

		}

	    }
	}
    }

    private boolean isUserAllowedForApplication(Method method, Session session) {

	SecurityService securityService = (SecurityService) applicationContext.getBean("securityService");
	
	/*
	 * if the role of the user is superadmin, he has the right to use any
	 * application
	 */
	if (session.getUser().getRole().equals(RoleEnum.SUPERADMIN.name())) {
	    return true;
	}

	boolean applicationNameAllowed = false;
	boolean applicationOsAllowed = false;
	boolean applicationConfigurationAllowed = false;

	List<fr.mystocks.mystockserver.data.security.Application> allowedApplications = securityService
		.getAllowedApplicationForUser(session.getUser());

	Application applicationConfiguration = method.getAnnotation(Application.class);

	for (fr.mystocks.mystockserver.data.security.Application application : allowedApplications) {
	    if (application.getName().toUpperCase().equals(applicationConfiguration.name().name())) {
		applicationNameAllowed = true;
		break;
	    }
	}

	/*
	 * if application is not allowed we continue by os/type configuration of
	 * the method
	 */
	if (applicationNameAllowed) {
	    for (fr.mystocks.mystockserver.data.security.Application application : allowedApplications) {
		/* is all OS are cover */
		if (application.getOs().toUpperCase().equals(applicationConfiguration.os().name())
			|| applicationConfiguration.os() == OS.ALL) {
		    applicationOsAllowed = true;
		    break;
		}

	    }
	}
	if (applicationOsAllowed) {
	    for (fr.mystocks.mystockserver.data.security.Application application : allowedApplications) {
		/* is all OS are cover */
		if (application.getAppType().toUpperCase().equals(applicationConfiguration.type().name())
			|| applicationConfiguration.type() == Type.ALL) {
		    applicationConfigurationAllowed = true;
		    break;
		}
	    }
	}
	return applicationConfigurationAllowed && applicationNameAllowed && applicationOsAllowed;
    }

    private Session getSessionAndCheckUserAllowed(String token, List<RoleEnum> rolesAllowed,
	    ContainerRequestContext requestContext) {
	SecurityService securityService = (SecurityService) applicationContext.getBean("securityService");
	Session session = securityService.getSessionFromToken(token);

	if (session == null) {
	    requestContext.abortWith(ACCESS_DENIED
		    .entity(messageSource.getMessage("error.authentication.access.denied", null,Locale.ENGLISH))
		    .build());
	    return null;
	}

	if (!checkRole(session.getUser().getRole(), rolesAllowed)) {
	    requestContext.abortWith(ACCESS_FORBIDDEN.entity(
		    messageSource.getMessage("error.authentication.access.forbidden", null, Locale.ENGLISH))
		    .build());
	    return null;
	}
	return session;
    }

    private boolean checkRole(String role, List<RoleEnum> rolesAllowed) {
	for (RoleEnum roleAllowed : rolesAllowed) {
	    if (roleAllowed.name().equals(role.toUpperCase())) {

		return true;
	    }
	}
	return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	this.applicationContext = applicationContext;
    }

}
