package fr.mystocks.mystockserver.functional.finance.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.context.MessageSource;

public class StockUtils {
	public static final Pattern ISIN_PATTERN = Pattern.compile("[a-zA-Z]{2}([a-zA-Z0-9]){9}[0-9]");

	public static final Pattern ISIN_PATTERN_START = Pattern.compile("[A-Z]{2}");

	public static final Integer ISIN_LENGTH = 12;

	/**
	 *
	 */
	private StockUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author sauzanne
	 * 
	 * @param isin
	 * @return
	 */
	public static List<String> checkISIN(String isin, String parameterName, MessageSource messageSource,
			Locale locale) {

		List<String> messages = new ArrayList<>();
		if (isin.length() != ISIN_LENGTH) {
			messages.add(messageSource.getMessage("error.param.format.length",
					new String[] { parameterName, ISIN_LENGTH.toString() }, locale));
		}
		if (!ISIN_PATTERN.matcher(isin).matches()) {

			messages.add(messageSource.getMessage("error.isin.format.start", null, locale));
		}

		if (!checkIsinCode(isin)) {
			messages.add(messageSource.getMessage("error.format.bad", new String[] { parameterName }, locale));

		}
		return messages;

	}

	/**
	 * @author sauzanne
	 * 
	 * @param isin
	 * @return
	 */
	public static boolean checkIsinCode(String isin) {
		if (isin == null) {
			return false;
		}
		if (!ISIN_PATTERN.matcher(isin).matches()) {
			return false;
		}

		StringBuffer digits = new StringBuffer();
		for (int i = 0; i < 11; i++) {
			digits.append(Character.digit(isin.charAt(i), 36));
		}
		digits.reverse();
		int sum = 0;
		for (int i = 0; i < digits.length(); i++) {
			int digit = Character.digit(digits.charAt(i), 36);
			if (i % 2 == 0) {
				digit *= 2;
			}
			sum += digit / 10;
			sum += digit % 10;
		}

		int checkDigit = Character.digit(isin.charAt(11), 36);
		int tensComplement = (sum % 10 == 0) ? 0 : ((sum / 10) + 1) * 10 - sum;
		return checkDigit == tensComplement;
	}

}
