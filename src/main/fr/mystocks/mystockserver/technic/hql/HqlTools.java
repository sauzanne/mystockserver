package fr.mystocks.mystockserver.technic.hql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe utilitaire pour les requêtes HQL.
 * 
 * @author Umanis
 */
public final class HqlTools {

	/** . */
	//TODO : paramètre à surveiller dans le cadre de la surcharge serveur -> proposer une alernative quand il y a trop de requêtes
	public static final int NOMBRE_ARGUMENTS_MAX_RECHERCHE = 256;
	public static final int NOMBRE_ARGUMENTS_MAX_CREATION = 1024;
	public static final int MAX_ENTRIES_2048 = 2048;

	public static final int NOMBRE_DE_CHAMPS_2 = 2;
	
	public static final List<Set<String>> diacriticalValues;
	static {
		diacriticalValues = new ArrayList<Set<String>>();
		diacriticalValues.add(new HashSet<String>(Arrays.asList("à", "â", "ä", "a")));
		diacriticalValues.add(new HashSet<String>(Arrays.asList("é", "è", "ê", "e", "ë")));
		diacriticalValues.add(new HashSet<String>(Arrays.asList("ï", "î", "i")));
		diacriticalValues.add(new HashSet<String>(Arrays.asList("ô", "ö", "o")));
		diacriticalValues.add(new HashSet<String>(Arrays.asList("ù", "û", "ü", "u")));
		diacriticalValues.add(new HashSet<String>(Arrays.asList("ÿ", "y")));
		diacriticalValues.add(new HashSet<String>(Arrays.asList("ç", "c")));
	}

	/** Constructeur par défaut. */
	private HqlTools() {
	}



	/**
	 * Teste si la valeur est d'un type différent de
	 * <code>String, Long, List</code>. On ne teste donc que si elle est
	 * différente de null dans la préapration des requêtes.
	 * 
	 * @param value L'objet à tester
	 * @return Vrai si l'objet n'est pas de type une
	 * <code>String, Long, List</code>
	 */
	public static boolean isNotTestable(Object value) {
		return !(value instanceof String) && !(value instanceof Long) && !(value instanceof List);
	}


	/**
	 * Mise à jour d'une requête HQL avec ajout de 'where' ou 'and' en fonction
	 * de contenue de la requête.
	 * 
	 * @param sb La requête au format StringBuffer
	 */
	public static void getClauseWhere(StringBuilder sb) {
		sb.append(!sb.toString().toLowerCase().contains("where") ? "where " : " and ");
	}
}
