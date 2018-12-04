package fr.mystocks.mystockserver.technic.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import fr.mystocks.mystockserver.data.security.constant.ApplicationEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD}) //on class level
public @interface Application {

	public enum Type {
	   APP, WEB, SOFTWARE, ALL
	}
	
	public enum OS{
	    WIN, ANDROID, IOS, ALL;
	}
	/* the default configuration is based on Mystocks */
	Type type() default Type.SOFTWARE;	
	OS os() default OS.WIN;
	ApplicationEnum name() default ApplicationEnum.MYSTOCKS;

}