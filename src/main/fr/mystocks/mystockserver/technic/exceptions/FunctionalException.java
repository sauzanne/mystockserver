package fr.mystocks.mystockserver.technic.exceptions;

/**
 * Gestion des exceptions MyStocks
 * 
 * @author auzanne
 * 
 */
public class FunctionalException extends BaseException {

	private static final long serialVersionUID = 3492846661127850339L;

	/**
	 * la clé d'une properties d'erreur
	 */
	private String keyError;

	/**
	 * liste des arguments du message (optionnel)
	 */
	private String[] args;

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	/**
	 * @author sauzanne @return the keyError
	 */
	public String getKeyError() {
		return keyError;
	}

	/**
	 * @author sauzanne @param keyError the keyError to set
	 */
	public void setKeyError(String keyError) {
		this.keyError = keyError;
	}

	/**
	 * Créer une exception fonctionnelle à partir d'une exception déjà existante
	 * 
	 * @param source
	 *            l'objet où est déclenché l'erreur
	 * @param e
	 *            une exception fonctionnelle
	 */
	public FunctionalException(Object source, FunctionalException e) {
		super(source);
		this.initException = e.getInitException();
		this.keyError = e.getKeyError();
	}

	/**
	 * Crée une nouvelle exception fonctionnelle
	 * 
	 * @param source
	 *            la source
	 * @param keyError
	 *            la clé de properties de l'erreur
	 */
	public FunctionalException(Object source, String keyError) {
		super(source);
		this.keyError = keyError;
	}

	/**
	 * Crée une nouvelle exception fonctionnelle
	 * 
	 * @param source
	 *            la source
	 * @param keyError
	 *            la clé de properties de l'erreur
	 */
	public FunctionalException(Object source, String keyError, String[] args) {
		super(source);
		this.keyError = keyError;
		this.args = args;
	}

}
