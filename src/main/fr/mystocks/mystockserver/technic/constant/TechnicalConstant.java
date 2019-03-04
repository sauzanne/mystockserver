package fr.mystocks.mystockserver.technic.constant;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Définir ici toutes les constantes techniques
 * 
 * @author sauzanne
 *
 */
public final class TechnicalConstant {

    public final static String ENCODING = "UTF-8";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String AMPERSAND_SEPARATOR = "&";
    public static final String EQUALS = "=";
    public static final String QUESTION = "?";
    public static final String NULL = "null";
    public static final String PROPERTIES_ZERO = "{0}";
    public static final String PROPERTIES_ONE = "{1}";
    public static final String COMMA = ",";
    public static final String BRACE_RIGHT = "}";
    public static final String CHECKBOX_ON = "on";
    private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

    private TechnicalConstant() {
	super();
    }

    public static boolean isWrapperType(Class<?> clazz) {
	return WRAPPER_TYPES.contains(clazz);
    }

    private static Set<Class<?>> getWrapperTypes() {
	Set<Class<?>> ret = new HashSet<Class<?>>();
	ret.add(Boolean.class);
	ret.add(Character.class);
	ret.add(Byte.class);
	ret.add(Short.class);
	ret.add(Integer.class);
	ret.add(Long.class);
	ret.add(Float.class);
	ret.add(Double.class);
	ret.add(Void.class);
	ret.add(String.class);
	ret.add(LocalDateTime.class);

	return ret;
    }

}
