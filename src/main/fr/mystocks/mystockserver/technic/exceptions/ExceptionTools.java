package fr.mystocks.mystockserver.technic.exceptions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.mystocks.mystockserver.technic.properties.PropertiesTools;

@Component
public final class ExceptionTools {

	private static PropertiesTools propertiesTools;

	/**
	 *
	 */
	@Autowired
	private ExceptionTools(PropertiesTools propertiesTools) {
		super();
		ExceptionTools.propertiesTools = propertiesTools;
	}

	/**
	 * @author sauzanne
	 * 
	 * @param source
	 * @param logger
	 * @param initException
	 */
	public static void processException(Object source, Logger logger, Exception initException) {
		// si on a une exception de type FunctionException on ne la logge pas et on la
		// throw telle qu'elle
		if (initException != null && initException instanceof FunctionalException) {
			throw (FunctionalException) initException;
		}
		BaseException b = new BaseException(source, initException);
		logger.error(propertiesTools.getProperty("error.technical",
				new String[] { source.getClass().getCanonicalName(), propertiesTools.getProperty("common.unknown") }),
				b.getInitException());
		throw b;
	}

	/**
	 * @author sauzanne
	 * 
	 * @param source
	 * @param logger
	 * @param initException
	 */
	public static void processExceptionOnlyWithLogging(Object source, Logger logger, Exception initException) {
		BaseException b = new BaseException(source, initException);
		logger.error(propertiesTools.getProperty("error.technical",
				new String[] { source.getClass().getCanonicalName(), propertiesTools.getProperty("common.unknown") }),
				b.getInitException());
	}

	/**
	 * Parse la trace.
	 * 
	 * @param exception
	 *            L'exception à parses
	 * @return Le nom de l'exception
	 */
	public static String parseTrace(Exception exception) {
		if (exception != null) {
			ByteArrayOutputStream boa = new ByteArrayOutputStream();
			exception.printStackTrace(new PrintStream(boa));
			return boa.toString();
		} else {
			return "";
		}
	}

}
