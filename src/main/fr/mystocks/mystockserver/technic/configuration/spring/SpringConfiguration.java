package fr.mystocks.mystockserver.technic.configuration.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;

@Configuration
@ComponentScan(basePackages = "fr.mystocks.mystockserver")
@EnableCaching
@EnableScheduling
public class SpringConfiguration {

	@Autowired
	private Environment environment;

	private Locale locale;

	@Bean
	public JCacheCacheManager cacheManager() throws URISyntaxException {
		JCacheCacheManager jCacheCacheManager = new JCacheCacheManager(getJCacheManagerFactoryBean().getObject());
		return jCacheCacheManager;
	}

	@Bean
	public JCacheManagerFactoryBean getJCacheManagerFactoryBean() throws URISyntaxException {
		JCacheManagerFactoryBean jCacheManagerFactoryBean = new JCacheManagerFactoryBean();
		jCacheManagerFactoryBean
				.setCacheManagerUri(new URI("classpath:/ehcache.xml"));
		return jCacheManagerFactoryBean;
	}

	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(new String[] { "classpath:properties/error", "classpath:properties/common",
				"classpath:properties/measure" });
		messageSource.setDefaultEncoding(TechnicalConstant.ENCODING);
		return messageSource;
	}

	public Locale getLocale() {
		if (locale == null) {
			locale = Locale.ENGLISH;
		}
		return locale;
	}
	
	@PreDestroy
	public void onDestroy() {
		System.out.println("Spring Container is destroyed!");
	}


	/*
	 * @Bean public LocaleResolver localeResolver() { CookieLocaleResolver resolver
	 * = new CookieLocaleResolver(); resolver.setDefaultLocale(new Locale("en"));
	 * resolver.setCookieName("myLocaleCookie"); resolver.setCookieMaxAge(4800);
	 * return resolver; }
	 * 
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
	 * interceptor.setParamName("locale"); registry.addInterceptor(interceptor); }
	 */
}
