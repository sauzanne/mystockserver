package fr.mystocks.mystockserver.technic.properties;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import fr.mystocks.mystockserver.technic.configuration.spring.SpringConfiguration;

@Component
public final class PropertiesTools {

    @Inject
    private SpringConfiguration context;

    @Autowired
    private MessageSource messageSource;

    /**
     *
     */
    public PropertiesTools() {
	super();
    }

    /**
     * @author sauzanne
     * 
     * @param key
     * @return
     */
    public String getProperty(String key) {
	return messageSource.getMessage(key, null, context.getLocale());
    }

    /**
     * @author sauzanne
     * 
     * @param key
     * @param args
     * @return
     */
    public String getProperty(String key, String... args) {
	// context.get
	//
	// String res = context.getEnvironment().getProperty(key);
	//
	// if (args != null && args.length > 0) {
	// for (String arg : args) {
	// res = res.replaceFirst(TechnicalConstant.PROPERTIES_ZERO, arg);
	// }
	// }
	return messageSource.getMessage(key, args, context.getLocale());
    }

}
