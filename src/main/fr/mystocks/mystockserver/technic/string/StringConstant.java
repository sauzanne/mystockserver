package fr.mystocks.mystockserver.technic.string;

/**
 * Liste des constantes pour les contrôles de surfaces.
 */
public final class StringConstant {

    /** . */
    private StringConstant() {
    }

    /**
     * Défini les caractères qui sont autorisés en général pour les saisies dans
     * l'application. Cette définition est volontairement très large car elle
     * s'applique que lorsqu'il n'y en a pas de plus précise.
     */
    public static final String CHAINE_CARACTERES_AUTORISES = "\\d\\w éèçàùâêîôûäëïöüÿè'.,!:()?&\"{}@\\[\\]/+*=%<>\\-\r\n";

    /**
     * Expression régulière pour indiquer les caractères spéciaux. Comme on
     * procède par négation (avec le "^"), ce qui est contenu dans l'expression
     * régulière représente l'esemble des caractères autorisés.
     */
    public static final String CARACTERES_SPECIAUX = "[^" + CHAINE_CARACTERES_AUTORISES + "]";

    /**
     * Caractères interdits pour l'enregistrement de données provenant de zones
     * de texte type "commentaires". On autorise en plus les sauts de ligne.
     */
    public static final String CARACTERES_INTERDITS_ZONE_TEXTE = CARACTERES_SPECIAUX;

    /**
     * Caractères interdits pour l'enregistrement en base des données texte
     * classiques.
     */
    public static final String CARACTERES_INTERDITS_STRING = "[^\\d\\w éèçàùâêîôûäëïöüÿè'.,\":;\\-/\\-*]";

    /**
     * Caractères interdits pour l'enregistrement en base des noms.
     */
    public static final String CARACTERES_INTERDITS_NOM = "[[^a-zA-Z]]";

    /**
     * Caractère interdits dans les mails. Il doivent correspondre avec le
     * pattern spécifié dans Note : on utilise également la négation dans ce
     * cas.
     */
    public static final String CARACTERES_INTERDITS_MAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Caractères interdits pour l'enregistrement en base des identifiants.
     */
    public static final String CARACTERES_INTERDITS_IDENTIFIANT = "[^\\d\\wéèçàùâêîôûäëïöüÿè_.-]";

    /**
     * Caractéres interdits pour l'enregistrement en base des téléphones.
     */
    public static final String CARACTERES_INTERDITS_TELEPHONE = "^([0-90-9]{2,2}['.']){4,4}[0-90-9]{2,2}$";
    public static final String CARACTERES_INTERDITS_TELEPHONE_SANS_POINT = "^([0-90-9]{2,2}){4,4}[0-90-9]{2,2}$";
    public static final String CARACTERES_INTERDITS_TELEPHONE_AVEC_ESPACE = "^([0-90-9]{2,2}['\\s']){4,4}[0-90-9]{2,2}$";

    public static final String TELEPHONE_INTERNATIONAL = "((\\+[1-9]{3,4}|0[1-9]{4}|00[1-9]{3})\\-?)?\\d{8,13}";

    /**
     * Longueur autorisée pour les numéros de téléphone francais.
     */
    public static final int TAILLE_NUMERO_TELEPHONE = 10;

    /**
     * Format de code postal accepté pour les recherches format: 9* ou 95* ou
     * 950* ou 9500* ou 95000.
     */
    public static final String CODE_POSTAL_GENERIQUE_VALIDE = "(^\\d{5}$)|(^\\d{1,4}\\*$)";

    /**
     * Format de code postal accepté.
     */
    public static final String CODE_POSTAL_VALIDE = "(^[0-9][1-9][0-9]{3}$)|(^[1-9][0-9]{4}$)";

    /**
     * Format de code comité valide.
     */
    public static final String CODE_COMITE_VALIDE = "\\d{4}";

    /**
     * Format de code ligue valide.
     */
    public static final String CODE_LIGUE_VALIDE = "^[a-zA-Z]{3}$";

    /** . */
    public static final String NOMBRES = "0123456789";
    /** . */
    public static final String ALPHABET_MAJUSCULE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /** . */
    public static final String ALPHABET_MAJUSCULE_EMARQUE = "ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * Format de code postal accepté.
     */
    public static final int MDP_NB_CARACTERE = 4;

    /**
     * Format de code postal accepté.
     */
    public static final int MDP_SIMILARITE = 4;

    /** Taille minimum du mot de passe. */
    public static final int TAILLE_MDP_MIN = 8;

    public static final int TAILLE_MDP_20_CHARS = 20;

    /** Taille maximum du mot de passe. */
    public static final int TAILLE_MDP_MAX = 12;

    /**
     * Format de code postal accepté.
     */
    public static final int TAILLE_CODE_INTERNET = 10;

    public static final int TAILLE_CODE_MATCH = 8;

    /** Taille d'un champ de 5 caractères. */
    public static final int TAILLE_CHAMP_5 = 5;

    /** Taille d'un champ de 30 caractères. */
    public static final int TAILLE_CHAMP_30 = 30;

    /** Taille d'un champ de 32 caractères. */
    public static final int TAILLE_CHAMP_32 = 32;

    /** Taille d'un champ de 60 caractères. */
    public static final int TAILLE_CHAMP_60 = 60;

    /** Taille d'un champ de 64 caractères. */
    public static final int TAILLE_CHAMP_64 = 64;

    /** Taille d'un champ de 100 caractères. */
    public static final int TAILLE_CHAMP_100 = 100;

    /** Taille d'un champ de 255 caractères. */
    public static final int TAILLE_CHAMP_255 = 255;
    /**
     * Format d'un nombre decimal accepté. jusqu'à 6 caractères avant virgule et
     * 2 caractères après virgule
     */
    public static final String DECIMAL_NOMBRE_FORMAT = "\\d{0,6}\\.\\d{0,2}";

}