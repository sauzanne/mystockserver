package fr.mystocks.mystockserver.technic.configuration.rest;

import javax.ws.rs.ApplicationPath;


import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configuration
@ApplicationPath("api")
public class RestConfiguration extends ResourceConfig {

    public RestConfiguration() {
	//register(Binder.class);
	//property("contextConfig", new AnnotationConfigApplicationContext(SpringConfiguration.class, HibernateConfiguration.class));
        register(RequestContextFilter.class);

	packages("fr.mystocks.mystockserver.view");
	property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	register(JacksonJsonProvider.class);
	// register(AuthenticationFilter.class);
    }
}