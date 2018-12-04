package fr.mystocks.mystockserver.view;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;
import fr.mystocks.mystockserver.data.security.constant.RoleConst;
import fr.mystocks.mystockserver.technic.security.annotation.Application;
import fr.mystocks.mystockserver.technic.security.annotation.Application.OS;
import fr.mystocks.mystockserver.technic.security.annotation.Application.Type;

@Path("healthcheck")
public class HealthCheck {

    @GET
    public String doesItWorks() {
	return "It works!";
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("examplemethod")
    public String doesItWorksWithAuthentication() {
	return "It works with authentication";
    }
    
    @RolesAllowed(RoleConst.ADMIN)
    @Application(type=Type.SOFTWARE, os=OS.WIN, name=ApplicationEnum.MYSTOCKS)
    @GET
    @Path("examplemethodwithapp")
    public String exempleMethodWithApp() {
	return "It works with authentication and application";
    }

}