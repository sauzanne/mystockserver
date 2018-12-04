package fr.mystocks.mystockserver.view.model.security;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.mystocks.mystockserver.data.security.Account;
import fr.mystocks.mystockserver.technic.serializer.LocalDateTimeSerializer;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY // mandatory for serialization
)
public class AccountModel {

	@JsonProperty("lastModified")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastModified;

	@JsonProperty("firstInput")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime firstInput;

	@JsonProperty("lastConnection")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastConnection;

	private String mail;

	private String login;

	private String lastName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String firstName;
	
	private String token;

	private AccountTypeModel accountType;
	
	private Integer id;

	@JsonCreator
	public AccountModel(Integer id,LocalDateTime lastModified, LocalDateTime firstInput, LocalDateTime lastConnection, String mail,
			String login, String lastName, String firstName, String token, AccountTypeModel accountType) {
		super();
		this.id = id;
		this.lastModified = lastModified;
		this.firstInput = firstInput;
		this.lastConnection = lastConnection;
		this.mail = mail;
		this.login = login;
		this.lastName = lastName;
		this.firstName = firstName;
		this.accountType = accountType;
		this.token = token;
	}

	/**
	 *
	 */
	@JsonCreator
	public AccountModel() {
		super();
	}

	public void convertFormAccount(Account account) {
		BeanUtils.copyProperties(account, this);
		if (account.getAccountType() != null) {
			AccountTypeModel accountTypeModel = new AccountTypeModel();
			accountTypeModel.convertFormAccountType(account.getAccountType());
			this.setAccountType(accountTypeModel);
		}
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public LocalDateTime getFirstInput() {
		return firstInput;
	}

	public void setFirstInput(LocalDateTime firstInput) {
		this.firstInput = firstInput;
	}

	public LocalDateTime getLastConnection() {
		return lastConnection;
	}

	public void setLastConnection(LocalDateTime lastConnection) {
		this.lastConnection = lastConnection;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public AccountTypeModel getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountTypeModel accountType) {
		this.accountType = accountType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
