package fr.mystocks.mystockserver.data.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

/**
 * Custom Security Context.
 * 
 * @author Deisss (MIT License)
 */
public class MystockServerSecurityContext implements SecurityContext {
    private User user;
    private String scheme;

    public MystockServerSecurityContext(User user, String scheme) {
	this.user = user;
	this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {
	return this.user;
    }

    @Override
    public boolean isUserInRole(String s) {
	if (user.getRole() != null) {
	    return user.getRole().contains(s);
	}
	return false;
    }

    @Override
    public boolean isSecure() {
	return "https".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme() {
	return SecurityContext.BASIC_AUTH;
    }
}