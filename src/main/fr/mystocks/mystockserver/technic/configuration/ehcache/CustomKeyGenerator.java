/**
 * 
 */
package fr.mystocks.mystockserver.technic.configuration.ehcache;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

/**
 * Génération d'une clé de type String plus robuste que le SimpleKey
 * 
 * @author sauzanne
 *
 */
@Component
public class CustomKeyGenerator implements KeyGenerator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.cache.interceptor.KeyGenerator#generate(java.lang.
	 * Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object generate(Object target, Method method, Object... params) {
		return target.getClass().getSimpleName() + "_" + method.getName() + "_" + Arrays.deepHashCode(params);
	}

}
