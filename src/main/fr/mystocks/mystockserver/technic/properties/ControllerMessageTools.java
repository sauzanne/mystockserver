package fr.mystocks.mystockserver.technic.properties;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.google.common.base.Strings;

public class ControllerMessageTools {

	private MessageSource messageSource;

	private Locale locale;

	private List<String> errorMessages = new ArrayList<>();

	/**
	 *
	 * @param messageSource
	 * @param locale
	 */
	public ControllerMessageTools(MessageSource messageSource, Locale locale) {
		super();
		this.messageSource = messageSource;
		this.locale = locale;
	}

	/**
	 * @author sauzanne
	 * 
	 * @param valueToCheck
	 * @param parameterName
	 * @param locale
	 */
	@SuppressWarnings("rawtypes")
	public void checkEmptyParameter(Object value, String parameterName) {
		boolean empty = false;
		if (value == null) {
			empty = true;
		} else if (value instanceof String && Strings.isNullOrEmpty((String) value)) {
			empty = true;
		} else if (value instanceof List && ((List) value).isEmpty()) {
			empty = true;
		}

		if (empty) {
			errorMessages.add(messageSource.getMessage("error.param.empty", new String[] { parameterName }, locale));

		}
	}

	/**
	 * @author sauzanne
	 * 
	 * @param valueToCheck
	 * @param dateTimeFormatter
	 * @param parameterName
	 * @return
	 */
	public LocalDate validateDateFormatParameter(String valueToCheck, DateTimeFormatter dateTimeFormatter,
			String parameterName) {

		if (!Strings.isNullOrEmpty(valueToCheck)) {

			try {
				return LocalDate.parse(valueToCheck, dateTimeFormatter);
			} catch (DateTimeParseException e) {
				errorMessages.add(
						messageSource.getMessage("error.param.date.format", new String[] { parameterName }, locale));
			}
		}

		return null;
	}

	/**
	 * Vérifie qu'un paramètre est au format BigDecimal et tente de le convertir
	 * 
	 * @author sauzanne
	 * 
	 * @param valueToCheck
	 *            le nombre à vérifier
	 * @param parameterName
	 *            le nom du paramètre
	 * @return la valeur au format bigdecimal si elle peut être convertie
	 */
	public BigDecimal validateBigDecimalParameter(String valueToCheck, String parameterName) {

		if (!Strings.isNullOrEmpty(valueToCheck)) {

			try {
				return new BigDecimal(valueToCheck);
			} catch (NumberFormatException e) {
				errorMessages.add(messageSource.getMessage("error.format.bad", new String[] { parameterName }, locale));
			}
		}

		return null;
	}
	
	/**
	 * Vérifie qu'un paramètre est au format BigInteger et tente de le convertir
	 * 
	 * @author sauzanne
	 * 
	 * @param valueToCheck
	 *            le nombre à vérifier
	 * @param parameterName
	 *            le nom du paramètre
	 * @return la valeur au format BigInteger si elle peut être convertie
	 */
	public BigInteger validateBigIntegerParameter(String valueToCheck, String parameterName) {

		if (!Strings.isNullOrEmpty(valueToCheck)) {

			try {
				return new BigInteger(valueToCheck);
			} catch (NumberFormatException e) {
				errorMessages.add(messageSource.getMessage("error.format.bad", new String[] { parameterName }, locale));
			}
		}

		return null;
	}

	
	
	/**
	 * Vérifie qu'un paramètre est au format double et tente de le convertir
	 * 
	 * @author sauzanne
	 * 
	 * @param valueToCheck
	 *            le nombre à vérifier
	 * @param parameterName
	 *            le nom du paramètre
	 * @return la valeur au format double si elle peut être convertie
	 */
	public Double validateDoubleParameter(String valueToCheck, String parameterName) {

		if (!Strings.isNullOrEmpty(valueToCheck)) {

			try {
				return new Double(valueToCheck);
			} catch (NumberFormatException e) {
				errorMessages.add(messageSource.getMessage("error.format.bad", new String[] { parameterName }, locale));
			}
		}

		return null;
	}


	/**
	 * @author sauzanne
	 * 
	 * @param valueDateBefore
	 * @param valueDateAfter
	 * @param parameterDateBefore
	 * @param parameterDateAfter
	 */
	public void checkDateBefore(LocalDate valueDateBefore, LocalDate valueDateAfter, String parameterDateBefore,
			String parameterDateAfter) {

		if (valueDateAfter != null && valueDateAfter.isBefore(valueDateBefore)) {
			errorMessages.add(messageSource.getMessage("error.param.date.before",
					new String[] { parameterDateBefore, parameterDateAfter }, locale));
		}
	}

	/**
	 * @author sauzanne
	 * 
	 * @param valueDateBefore
	 * @param parameterDateBefore
	 */
	public void checkDateBeforeNow(LocalDate valueDateBefore, String parameterDateBefore) {

		if (valueDateBefore.isAfter(LocalDate.now())) {
			errorMessages.add(messageSource.getMessage("error.param.date.before.current",
					new String[] { parameterDateBefore }, locale));
		}
	}
	
	
	/**
	 * Check if value1 >= value2
	 * @param value1 value of parameter 1
	 * @param value2 value of parameter 2
	 * @param parameterValue1 name of parameter 1
	 * @param parameterValue2 name of parameter 1
	 */
	public void checkValue1SuperiorOrEqualsValue2(Integer value1, Integer value2, String parameterValue1,
			String parameterValue2) {

		if (value1!=null && value2!=null && value1 < value2) {
			errorMessages.add(messageSource.getMessage("error.param.number.superiorequals",
					new String[] { parameterValue1, parameterValue2 }, locale));
		}
	}


	/**
	 * @author sauzanne @return the errorMessages
	 */
	public List<String> getErrorMessages() {
		return errorMessages;
	}

	/**
	 * Ajoute des mesasages depuis des sources externes
	 * 
	 * @author sauzanne
	 * 
	 * @param messages
	 */
	public void addErrorMessages(List<String> messages) {
		errorMessages.addAll(messages);
	}

}
