package fr.mystocks.mystockserver.technic.exceptions;

import java.io.Serializable;

/**
 * Exception basique sert de support plus évolué pour transporter des erreurs
 * techniques
 * 
 * @author sauzanne
 *
 */
public class BaseException extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2012617895431068541L;

	/**
	 * 
	 */

	protected Object source;

	protected Exception initException;

	public BaseException(Object source, Exception origin) {
		super();
		this.source = source;
		this.initException = origin;
	}

	protected BaseException(Object source) {
		super();
		this.source = source;
	}

	/**
	 * @author sauzanne @return the source
	 */
	public Object getSource() {
		return source;
	}

	/**
	 * @author sauzanne @param source the source to set
	 */
	public void setSource(Object source) {
		this.source = source;
	}

	/**
	 * @author sauzanne @return the initException
	 */
	public Exception getInitException() {
		return initException;
	}

	/**
	 * @author sauzanne @param initException the initException to set
	 */
	public void setInitException(Exception initException) {
		this.initException = initException;
	}

}
