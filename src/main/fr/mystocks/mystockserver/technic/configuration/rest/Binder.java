package fr.mystocks.mystockserver.technic.configuration.rest;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.springframework.context.MessageSource;

import fr.mystocks.mystockserver.service.security.SecurityServiceImpl;
import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;
import fr.mystocks.mystockserver.view.controller.security.AuthenticationFilter;

public class Binder extends AbstractBinder {

    @Override
    protected void configure() {
	bind(SecurityServiceImpl.class).to(AuthenticationFilter.class);
	bind(SpringConfiguration.class).to(AuthenticationFilter.class);
	bind(MessageSource.class).to(AuthenticationFilter.class);
    }

}
