package fr.mystocks.mystockserver.view.model.security;

import java.io.Serializable;

public class AccountCredentialModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5736148058829027447L;

	private String userName;
	
	private String password;
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
