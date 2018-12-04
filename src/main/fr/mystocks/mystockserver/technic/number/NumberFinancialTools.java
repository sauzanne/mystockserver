package fr.mystocks.mystockserver.technic.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFinancialTools {

    //public final static String NUMBER_DEFAULT_FORMAT_PATTERN = "###.###";

    private NumberFinancialTools() {
	super();
    }

    public static String defaultNumberFormat(BigDecimal numberToFormat, Locale locale) {
	NumberFormat nf = NumberFormat.getNumberInstance(locale);
	DecimalFormat df = (DecimalFormat) nf;
	return df.format(numberToFormat);
    }

}
