package fr.mystocks.mystockserver.technic.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;

/**
 * Utilitaire pour convertir les variables.
 * 
 * @author Umanis
 */
public final class NumberTools {

	private final static Logger logger = LoggerFactory.getLogger(NumberTools.class);
	private static final int POW_10 = 10;
	private static final int ARRONDI = 2;
	private static final String GUILLEMET = "\"";
	private static final int GROUP_SIZING_DEFAUT = 3;

	/** . */
	private NumberTools() {

	}

	/**
	 * Convertir un String en Double avec gestion de l'exception.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return La valeur en Double ou null
	 */
	public static Double parseDouble(String valeur) {
		try {
			if (valeur != null) {
				return Double.parseDouble(valeur.replace(" ", "").replaceAll(",", "."));
			}
		} catch (NumberFormatException e) {
		}
		return null;
	}

	/**
	 * Convertir un String en Float avec gestion de l'exception.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return La valeur en Float ou null
	 */
	public static Float parseFloat(String valeur) {
		try {
			if (valeur != null) {
				return Float.parseFloat(valeur.replace(" ", "").replaceAll(",", "."));
			}
		} catch (NumberFormatException e) {
		}
		return null;
	}

	/**
	 * Retourne 0 si un Double est null sinon la valeur du Double.
	 * 
	 * @param valeur
	 *            La valeur initiale
	 * @return 0 si null, sinon la valeur initiale
	 */
	public static Double getDoubleNeverNull(Double valeur) {
		return valeur == null ? 0D : valeur;
	}

	/**
	 * Retourne 0 si un Integer est null sinon la valeur de l'integer
	 * 
	 * @param valeur
	 *            La valeur initiale
	 * @return 0 si null, sinon la valeur initiale
	 */
	public static Integer getIntegerNeverNull(Integer valeur) {
		return valeur == null ? 0 : valeur;
	}

	/**
	 * Retourne 0 si un Short est null sinon la valeur du short
	 * 
	 * @param valeur
	 *            La valeur initiale
	 * @return 0 si null, sinon la valeur initiale
	 */
	public static Short getShortNeverNull(Short valeur) {
		return valeur == null ? 0 : valeur;
	}

	/**
	 * Retourne 0 si un Long est null sinon la valeur du Long
	 * 
	 * @param valeur
	 *            La valeur initiale
	 * @return 0 si null, sinon la valeur initiale
	 */
	public static Long getLongNeverNull(Long valeur) {
		return valeur == null ? 0L : valeur;
	}

	/**
	 * Arrondie un Double à 2 chiffres après la virgule.
	 * 
	 * @param value
	 *            La valeur à arrondir
	 * @return La valeur arrondie à 2 chiffres après la virgule
	 */
	public static Double arrondir(Double value) {
		double r = (Math.round(value.doubleValue() * Math.pow(POW_10, ARRONDI))) / (Math.pow(POW_10, ARRONDI));
		return new Double(r);
	}

	/**
	 * Arrondie un Double à 2 chiffres après la virgule.
	 * 
	 * @param value
	 *            La valeur à arrondir
	 * @return La valeur arrondie à 2 chiffres après la virgule
	 */
	public static String arrondir2Chiffres(Double value) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2); // arrondi à 2 chiffres apres la virgules
		df.setMinimumFractionDigits(2);
		df.setGroupingSize(GROUP_SIZING_DEFAUT);
		DecimalFormatSymbols s = df.getDecimalFormatSymbols();
		s.setGroupingSeparator(' ');
		df.setDecimalFormatSymbols(s);
		return df.format(value);
	}

	/**
	 * Retourne le premier caractere d'un string.
	 * 
	 * @param valeur
	 *            La chaine pour l'extraction
	 * @return Le premier caractère du string
	 */
	public static Character parseChar(String valeur) {
		if (!Strings.isNullOrEmpty(valeur)) {
			return valeur.toCharArray()[0];
		}
		return null;
	}

	/**
	 * Convertir un String en Long avec gestion de l'exception.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return La valeur de type String converti en Long (null si problème de
	 *         conversion)
	 */
	public static Long parseLong(String valeur) {
		try {
			if (!Strings.isNullOrEmpty(valeur)) {
				return Long.parseLong(valeur.trim());
			}
		} catch (NumberFormatException e) {
		}
		return null;
	}

	/**
	 * Convertir un String en Integer avec gestion de l'exception.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return La valeur de type String converti en Integer (null si problème de
	 *         conversion)
	 */
	public static Integer parseInteger(String valeur) {
		try {
			if (valeur != null) {
				return Integer.parseInt(valeur);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());

		}
		return null;
	}

	/**
	 * Convertir un Long en Integer avec gestion de l'exception.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return La valeur de type String converti en Integer (null si problème de
	 *         conversion)
	 */
	public static Integer parseInteger(Long valeur) {
		try {
			if (valeur != null) {
				return Integer.parseInt(valeur.toString());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return null;
	}

	/**
	 * Convertir un String en Integer avec gestion de l'exception.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return La valeur de type String converti en Integer (null si problème de
	 *         conversion)
	 */
	public static Integer parseInteger(Boolean valeur) {
		if (BooleanUtils.isTrue(valeur)) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Convertir un String en Boolean avec gestion de l'exception.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return La valeur de type String converti en Booléen (null si problème de
	 *         conversion)
	 */
	public static Boolean parseBoolean(String valeur) {
		try {
			if (valeur != null) {
				return Boolean.valueOf(valeur);
			}
		} catch (NumberFormatException e) {
		}
		return null;
	}

	/**
	 * Convertir un Long en Boolean avec gestion de l'exception (0 = false, 1 =
	 * true).
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return La valeur de type String converti en Booléen (null si problème de
	 *         conversion)
	 */
	public static Boolean parseBoolean(Long valeur) {
		if (valeur != null) {
			return valeur == 1L;
		}
		return false;
	}

	/**
	 * Convertir un String en Short avec gestion de l'exception.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return La valeur de type String converti en Booléen (null si problème de
	 *         conversion)
	 */
	public static Short parseShort(String valeur) {
		try {
			if (valeur != null) {
				return Short.valueOf(valeur);
			}
		} catch (NumberFormatException e) {
		}
		return null;
	}

	/**
	 * Convertion d'un objet en String.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return Le toString de la valeur
	 */
	public static String toString(Object valeur) {
		if (valeur != null) {
			return valeur.toString();
		}
		return null;
	}

	/**
	 * Convertion d'un objet en String.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @return Le toString de la valeur
	 */
	public static String toStringOuVide(Object valeur) {
		String retour = toString(valeur);
		return retour != null ? retour : "";
	}

	/**
	 * Teste qu'un Object n'est pas vide si c'est un String.
	 * 
	 * @param value
	 *            L'objet à tester
	 * @return Vrai si l'objet est un String et qu'il n'est pas vide
	 */
	public static boolean isNotChaineVide(Object value) {
		return (value instanceof String && !Strings.isNullOrEmpty(value.toString()));
	}

	/**
	 * Teste qu'un Object n'est pas égal à 0 si c'est un Long.
	 * 
	 * @param value
	 *            L'objet à tester
	 * @return Vrai si l'objet est un Long et qu'il n'est pas à 0
	 */
	public static boolean isNotNombreVide(Object value) {
		return (value instanceof Long && isDifferentDeZero((Long) value));
	}

	/**
	 * Teste si un objet est un nombre et s'il est différent de 0.
	 * 
	 * @param nombre
	 *            Le nombre à tester
	 * @return true si le nombre en entrée est différent de 0
	 */
	public static boolean isDifferentDeZero(Object nombre) {
		return nombre != null && Long.parseLong(nombre.toString()) != 0;
	}

	/**
	 * Teste si la valeur est d'un type différent de
	 * <code>String, Long, List</code>. On ne teste donc que si elle est différente
	 * de null dans la préapration des requêtes.
	 * 
	 * @param value
	 *            L'objet à tester
	 * @return Vrai si l'objet n'est pas de type une <code>String, Long, List</code>
	 */
	public static boolean isNotTestable(Object value) {
		return !(value instanceof String) && !(value instanceof Long) && !(value instanceof List);
	}

	/**
	 * Construit une list de String à partir d'un String splitté.
	 * 
	 * @param valeur
	 *            La valeur à convertir
	 * @param separateur
	 *            Le séparateur de la String
	 * @return La liste de String
	 */
	public static List<String> convertirStringEnList(String valeur, String separateur) {
		if (isNotChaineVide(valeur)) {
			List<String> resultat = new ArrayList<String>();
			if (isNotChaineVide(separateur)) {
				StringTokenizer stringTokenizer = new StringTokenizer(valeur, separateur);
				while (stringTokenizer.hasMoreTokens()) {
					resultat.add(stringTokenizer.nextToken());
				}
			} else {
				resultat.add(valeur);
			}
			return resultat;
		}
		return null;
	}

	/**
	 * Suppression des guillemets d'une chaine de caractères.
	 * 
	 * @param valeur
	 *            Une chaine de caractères
	 * @return La chaine de caractères sans guillemets
	 */
	public static String suppressionGuillemet(Object valeur) {
		if (valeur != null) {
			return valeur.toString().replace(GUILLEMET, "");
		}
		return null;
	}

	/**
	 * Suppression des guillemets d'une chaine de caractères.
	 * 
	 * @param valeur
	 *            Une chaine de caractères
	 * @return La chaine de caractères sans guillemets
	 */
	public static String suppressionAccent(String valeur) {
		if (valeur != null) {
			return Normalizer.normalize(valeur, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
		}
		return null;
	}

	/**
	 * Suppression des guillemets d'une chaine de caractères.
	 * 
	 * @param valeur
	 *            Une chaine de caractères
	 * @return La chaine de caractères sans guillemets
	 */
	public static String suppressionGuillemetCsv(Object valeur) {
		String valeurEscape = suppressionGuillemet(valeur);
		if (valeurEscape == null) {
			valeurEscape = "";
		}
		return "\"" + valeurEscape + "\"";
	}

	/**
	 * Convertit un BigDecimal en long
	 * 
	 * @param o
	 *            un nombre de type BigDecimal
	 * @return transforme le nombre en Long
	 */
	public static Long convertBigDecimalIntoLong(Object o) {
		return o != null ? new Long(((BigDecimal) o).toString()) : null;
	}

	/**
	 * Retourne null si id vaut 0.
	 * 
	 * @param id
	 *            L'identifiant à tester
	 * @return Null si id == 0, id sinon
	 */
	public static Long nullSiZero(Long id) {
		return (isDifferentDeZero(id)) ? id : null;
	}

}
