package fr.mystocks.mystockserver.technic.string;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.google.common.base.Strings;

import fr.mystocks.mystockserver.technic.number.NumberTools;

/**
 * Classe utilitaire pour les contrôles de surface.
 * 
 * @author Umanis
 */
public final class StringControlTools {

	/** . */
	private StringControlTools() {
	}

	/**
	 * Trim (suppression des espaces devant et derrière) d'une chaine.
	 * 
	 * @param chaine
	 *            La chaine
	 * @return Trim de la chaine (null si la chaine est null)
	 */
	public static String trim(String chaine) {
		if (chaine != null) {
			return chaine.trim();
		}
		return null;
	}

	/**
	 * Test le format d'une chaine en vérifiant que les caractères ne sont pas
	 * déhors du regex.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @param regex
	 *            Le regex inversé (tout sauf les expressions du regex)
	 * @return True si le format est correct, false sinon
	 */
	public static boolean isFormatCorrecte(String chaine, String regex) {

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(chaine);
		return !m.find();
	}

	/**
	 * Test le format d'une chaine en vérifiant que les caractères sont dans un
	 * regex.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @param regex
	 *            Le regex
	 * @return True si le regex est OK, false sinon
	 */
	public static boolean isRegexOk(String chaine, String regex) {

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(chaine);
		return m.matches();
	}

	/**
	 * Test la taille d'une chaine.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @param taille
	 *            La taille de la chaine
	 * @return True si la chaine est de la taille passée en paramètre, false sinon
	 */
	public static boolean isTaille(String chaine, int taille) {
		return chaine != null && chaine.length() == taille;
	}

	/**
	 * Test si la taille d'une chaine est inférieur à la taille donnée en paramètre.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @param taille
	 *            La taille de la chaine à tester
	 * @return True si la taille de la chaine est inférieur à la taille passée en
	 *         paramètre, false sinon
	 */
	public static boolean isTailleInferieur(String chaine, int taille) {
		return chaine.length() < taille;
	}

	/**
	 * Test si la taille d'une chaine est supérieure à la taille donnée en
	 * paramètre.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @param taille
	 *            La taille de la chaine à tester
	 * @return True si la taille de la chaine est supérieure à la taille passée en
	 *         paramètre, false sinon
	 */
	public static boolean isTailleSuperieur(String chaine, int taille) {
		return chaine.length() > taille;
	}

	/**
	 * Test si la taille d'une chaine est comprise entre les deux tailles données en
	 * paramètre.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @param tailleInf
	 *            La taille inférieure de la chaine à tester
	 * @param tailleSup
	 *            La taille supérieur de la chaine à tester
	 * @return True si la taille de la chaine est comprise entre tailleInf et
	 *         tailleSup, false sinon
	 */
	public static boolean isTailleEntreDeux(String chaine, int tailleInf, int tailleSup) {
		return chaine.length() >= tailleInf && chaine.length() <= tailleSup;
	}

	/**
	 * Teste si une chaîne n'est pas vide ou nulle.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @return True si la chaîne n'est pas viden False sinon.
	 */
	public static boolean stringNonVide(String chaine) {
		return !Strings.isNullOrEmpty(chaine);
	}

	/**
	 * Formatage d'une chaine de caractères et suppression des caractères interdits
	 * (avec troncage si besoin est).
	 * 
	 * @param chaine
	 *            La chaine à formater
	 * @param regexCaracteresInterdits
	 *            L'expression régulière des caractères interdits
	 * @param taille
	 *            La taille maximale de la chaine à ne pas dépasser
	 * @return La chaine formatée
	 */
	public static String formateTexte(String chaine, String regexCaracteresInterdits, Integer taille) {
		String valFormatter = "";
		if (stringNonVide(chaine)) {
			// remplacement des caractères interdits
			valFormatter = trim(chaine).replaceAll(regexCaracteresInterdits, "");
			// remplacement des retour à la ligne
			valFormatter = valFormatter.replaceAll("\\s+", " ");
			// si la taille de la chaine dépasse la taille maximale en paramètre
			if (taille != null && valFormatter.length() > taille) {
				valFormatter = valFormatter.substring(0, taille);
			}
		}
		return valFormatter;
	}

	/**
	 * Formatage d'une chaine issue d'une zone de texte.
	 * 
	 * @param chaine
	 *            La chaine à formater
	 * @return La chaine formatée
	 */
	public static String formateZoneTexte(String chaine) {
		return formateTexte(chaine, StringConstant.CARACTERES_INTERDITS_ZONE_TEXTE, null);
	}

	/**
	 * Formatage d'une chaine issue d'une zone de texte avec troncage si besoin est.
	 * 
	 * @param chaine
	 *            La chaine à formater
	 * @param taille
	 *            La taille maximum de la chaine
	 * @return La chaine formatée
	 */
	public static String formateZoneTexte(String chaine, Integer taille) {
		return formateTexte(chaine, StringConstant.CARACTERES_INTERDITS_ZONE_TEXTE, taille);
	}

	/**
	 * Test si une chaine est une zone de texte.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @return True si le regex d'une zone de texte est OK, false sinon
	 */
	public static boolean isZoneTexte(String chaine) {
		return isFormatCorrecte(chaine, StringConstant.CARACTERES_INTERDITS_ZONE_TEXTE);
	}

	/**
	 * Test si une chaine est un mail.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @return True si le regex d'un mail est OK, false sinon
	 */
	public static boolean isFormatMail(String chaine) {
		if (!Strings.isNullOrEmpty(chaine)) {
			EmailValidator emailvalidator = EmailValidator.getInstance();

			return emailvalidator.isValid(chaine);
		}
		return false;
	}

	/**
	 * Formatage d'une chaine de type numéro de téléphone.
	 * 
	 * @param chaine
	 *            La chaine à formater
	 * @return La chaine formatée
	 */
	public static String formateTelephone(String chaine) {
		return formateTexte(chaine, StringConstant.CARACTERES_INTERDITS_TELEPHONE, null);
	}

	/**
	 * Test si une chaine est numéro de téléphone.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @return True si le regex d'un mail est OK, false sinon
	 */
	public static boolean isFormatTelephone(String chaine) {
		return isRegexOk(chaine, StringConstant.CARACTERES_INTERDITS_TELEPHONE)
				|| isRegexOk(chaine, StringConstant.CARACTERES_INTERDITS_TELEPHONE_SANS_POINT)
				|| isRegexOk(chaine, StringConstant.CARACTERES_INTERDITS_TELEPHONE_AVEC_ESPACE);
	}

	/**
	 * Test si une chaine est numéro de téléphone international.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @return True si le regex d'un mail est OK, false sinon
	 */
	public static boolean isFormatTelephoneInternational(String chaine) {
		return isRegexOk(chaine, StringConstant.TELEPHONE_INTERNATIONAL);
	}

	/**
	 * Test si une chaine a le format décimal spécifié
	 * 
	 * @param value
	 *            La chaine à tester
	 * @return Faux si valeur a le format spécifié , vrai sinon
	 */
	public static boolean isFormatDecimal(String value) {
		if (!Strings.isNullOrEmpty(value)) {
			return !isRegexOk(NumberTools.parseDouble(value).toString(), StringConstant.DECIMAL_NOMBRE_FORMAT);
		}
		return false;
	}

	/**
	 * Formatage d'une chaine de type nom de famille.
	 * 
	 * @param chaine
	 *            La chaine à formater
	 * @return La chaine formatée
	 */
	public static String formateNom(String chaine) {

		String nomFormatter = formateTexte(chaine, StringConstant.CARACTERES_SPECIAUX, StringConstant.TAILLE_CHAMP_60)
				.toUpperCase();
		return NumberTools.suppressionAccent(nomFormatter);
	}

	/**
	 * Test si une chaine est un nom de famille.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @return True si le regex d'un nom de famille est OK, false sinon
	 */
	public static boolean isFormatNom(String chaine) {

		return isFormatCorrecte(chaine, StringConstant.CARACTERES_SPECIAUX); // &&
		// formateNom(val).equals(val)
	}

	/**
	 * Formatage d'une chaine de type prénom.
	 * 
	 * @param chaine
	 *            La chaine à formater
	 * @return La chaine formatée
	 */
	public static String formatePrenom(String chaine) {
		if (chaine == null) {
			return null;
		}
		String chaineFormatter = formateTexte(chaine.toLowerCase(), StringConstant.CARACTERES_SPECIAUX,
				StringConstant.TAILLE_CHAMP_30);
		StringBuilder prenom = new StringBuilder();
		chaineFormatter = trim(chaineFormatter);
		StringTokenizer token = new StringTokenizer(chaineFormatter, " ");
		while (token.hasMoreTokens()) {
			prenom.append(capitalize(token.nextToken())).append(" ");
		}
		final String prenomFormatter = trim(prenom.toString());
		return NumberTools.suppressionAccent(prenomFormatter);
	}

	/**
	 * Test si une chaine est un prénom.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @return True si le regex d'un prénom est OK, false sinon
	 */
	public static boolean isFormatPrenom(String chaine) {

		return isFormatCorrecte(chaine, StringConstant.CARACTERES_SPECIAUX); // &&
		// formatePrenom(chaine).equals(chaine)
	}

	/**
	 * Transforme la première lettre d'une chaine en majuscule.
	 * 
	 * @param chaine
	 *            la chaine à formater
	 * @return La chaine avec sa première lettre en majuscule
	 */
	public static String capitalize(String chaine) {
		return chaine.replaceFirst(chaine.substring(0, 1), chaine.substring(0, 1).toUpperCase());
	}

	/**
	 * Formatage d'une chaine de type idenfitiant.
	 * 
	 * @param chaine
	 *            La chaine à formater
	 * @return La chaine formatée
	 */
	public static String formateIdentifiant(String chaine) {

		return formateTexte(chaine, StringConstant.CARACTERES_INTERDITS_IDENTIFIANT, StringConstant.TAILLE_CHAMP_60);
	}

	/**
	 * Test si une chaine est un idenfitiant.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @return True si le regex d'un prénom est OK, false sinon
	 */
	public static boolean isFormatIdentifiant(String chaine) {

		return isFormatCorrecte(chaine, StringConstant.CARACTERES_INTERDITS_IDENTIFIANT);
	}

	/**
	 * Formatage d'une chaine de type formatage.
	 * 
	 * @param chaine
	 *            La chaine à formater
	 * @return La chaine formatée
	 */
	public static String formateMotDePasse(String chaine) {

		return formateTexte(chaine, StringConstant.CARACTERES_INTERDITS_IDENTIFIANT, null);
	}

	/**
	 * Formatage d'un montant de la base en texte sous le format "00,00".
	 * 
	 * @param montant
	 *            Le montant à formatter
	 * @return Le montant formaté pour l'affichage
	 */
	public static String formateMontant(Double montant) {
		return montant != null ? new DecimalFormat("00.00").format(montant) : null;
	}

	/**
	 * Formatage d'un montant de la base en texte
	 * 
	 * @param montant
	 *            Le montant à formatter
	 * @return Le montant formaté pour l'affichage
	 */
	public static String formateMontantLocale(Double montant) {
		NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
		return montant != null ? nf.format(montant) : null;
	}

	/**
	 * Formatage iban avec espaces
	 * 
	 * @param iban
	 *            Le iban à formatter
	 * @return Le iban formaté pour l'affichage
	 */
	public static String formateIBANAvecEspaces(String iban) {
		String valFormatter = "";
		if (stringNonVide(iban)) {
			valFormatter = iban.replaceAll("(.{4})", "$1 ");
		}
		return valFormatter;
	}

	/**
	 * Formatage iban sans espaces
	 * 
	 * @param iban
	 *            Le iban à formatter
	 * @return Le iban formaté pour l'affichage
	 */
	public static String formateIBANSansEspaces(String iban) {
		String valFormatter = "";
		if (stringNonVide(iban)) {
			valFormatter = iban.replaceAll("\\s+", "");
		}
		return valFormatter;

	}

	/**
	 * Test si une chaine est un mot de passe.
	 * 
	 * @param chaine
	 *            La chaine à tester
	 * @return True si le regex du mot de passe est OK, false sinon
	 */
	public static boolean isFormatMotDePasse(String chaine) {

		return isFormatCorrecte(chaine, StringConstant.CARACTERES_INTERDITS_IDENTIFIANT);
	}

	/**
	 * Echappement des apostrophes pour le javascript.
	 * 
	 * @param chaine
	 *            La chaine
	 * @return La chaine avec des apostrophes pour le javascript
	 */
	public static String escapeApostrophe(String chaine) {
		return chaine != null ? chaine.replace("\'", "&apos;") : "";
	}

	/**
	 * Echappement des apostrophes pour le javascript.
	 * 
	 * @param chaine
	 *            La chaine
	 * @return La chaine avec des apostrophes pour le javascript
	 */
	public static String escapeJavascript(String chaine) {
		return chaine != null ? chaine.replace("\'", "\\\'").replace("\"", "") : "";
	}

	/**
	 * Echappement des double quotes
	 * 
	 * @param chaine
	 *            La chaine
	 * @return La chaine
	 */
	public static String escapeDoubleQuotes(String chaine) {
		return chaine != null ? chaine.replace("\"", "") : "";
	}

	/**
	 * Test si le mot de passe est valable (doit contenir un caractère et un
	 * chiffre).
	 * 
	 * @param mdp
	 *            Le mot de passe
	 * @param isTestCaractere
	 *            Test si c'est un caratère (sinon un chiffre)
	 * @return True si le test est OK
	 */
	public static boolean isMotDePasseValable(String mdp, boolean isTestCaractere) {
		for (int i = 0; i < mdp.length(); i++) {
			if (Character.isLetter(mdp.charAt(i)) && isTestCaractere) {
				return true;
			}
			if (org.apache.commons.lang.StringUtils.isNumeric(NumberTools.toString(mdp.charAt(i)))
					&& !isTestCaractere) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Test si un mot de passe est valable c'est à dire qu'il a 4 caractères
	 * numériques.
	 * 
	 * @param mdp
	 *            Le mot de passe
	 * @return True si le mot de passe est valable, False sinon
	 */
	public static boolean isMotDePasseValable(String mdp) {
		int nbCaractereNumerique = 0;
		for (int i = 0; i < mdp.length(); i++) {
			if (!Character.isLetter(mdp.charAt(i))) {
				nbCaractereNumerique++;
			}
		}
		return nbCaractereNumerique == StringConstant.MDP_NB_CARACTERE;
	}

	/**
	 * Test si deux mot de passe sont semblables (la moitiée de similarité).
	 * 
	 * @param mdpAncien
	 *            Le mot de passe ancien
	 * @param mdp
	 *            Le nouveau mot de passe
	 * @return True s'ils sont semblables, False sinon
	 */
	public static boolean isMotDePasseSemblable(String mdpAncien, String mdp) {
		return StringUtils.isNotBlank(mdpAncien) && mdpAncien.equals(mdp);
	}

	/**
	 * Génération d'une chaine alphanumérique de x caractères.
	 * 
	 * @return Le mot de passe
	 */
	public static String generateMotDePasse(Integer taille) {
		StringBuilder mdp = new StringBuilder();
		String nombres = "123456789";
		String chars = "abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
		int nbNombre = 0;
		int nbChar = 0;
		String traitement = null;
		int nbChoix = 2;
		for (int i = 0; i < taille; i++) {
			int choix = 0;
			if (nbNombre < StringConstant.MDP_NB_CARACTERE && nbChar < StringConstant.MDP_NB_CARACTERE) {
				choix = (int) (Math.random() * nbChoix);
			} else if (nbNombre >= StringConstant.MDP_NB_CARACTERE) {
				choix = 1;
			} else if (nbChar >= StringConstant.MDP_NB_CARACTERE) {
				choix = 0;
			}
			if (choix == 0) {
				traitement = nombres;
				nbNombre++;
			} else {
				traitement = chars;
				nbChar++;
			}
			mdp.append(traitement.charAt((int) (Math.random() * traitement.length())));
		}
		return mdp.toString();
	}

	/**
	 * Echappement des apostrophes pour le sql.
	 * 
	 * @param chaine
	 *            La chaine
	 * @return La chaine avec des apostrophes pour le sql
	 */
	public static String escapeApostropheSql(String chaine) {
		return chaine != null ? chaine.replace("'", "''") : "";
	}

	/**
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	public static int levenshteinDistance(String lhs, String rhs) {
		int len0 = lhs.length() + 1;
		int len1 = rhs.length() + 1;

		// the array of distances
		int[] cost = new int[len0];
		int[] newcost = new int[len0];

		// initial cost of skipping prefix in String s0
		for (int i = 0; i < len0; i++)
			cost[i] = i;

		// dynamically computing the array of distances

		// transformation cost for each letter in s1
		for (int j = 1; j < len1; j++) {
			// initial cost of skipping prefix in String s1
			newcost[0] = j;

			// transformation cost for each letter in s0
			for (int i = 1; i < len0; i++) {
				// matching current letters in both strings
				int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

				// computing cost for each transformation
				int cost_replace = cost[i - 1] + match;
				int cost_insert = cost[i] + 1;
				int cost_delete = newcost[i - 1] + 1;

				// keep minimum cost
				newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
			}

			// swap cost/newcost arrays
			int[] swap = cost;
			cost = newcost;
			newcost = swap;
		}

		// the distance is the cost for transforming all letters in both strings
		return cost[len0 - 1];
	}
}