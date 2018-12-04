package fr.mystocks.mystockserver.data.security;

import java.security.Principal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mystockserver.server_user")
public class User implements Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    /**
     * @return the login
     */
    public String getLogin() {
	return login;
    }

    /**
     * @param login
     *            the login to set
     */
    public void setLogin(String login) {
	this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @return the role
     */
    public String getRole() {
	return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(String role) {
	this.role = role;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((login == null) ? 0 : login.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((role == null) ? 0 : role.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof User)) {
	    return false;
	}
	User other = (User) obj;
	if (id != other.id) {
	    return false;
	}
	if (login == null) {
	    if (other.login != null) {
		return false;
	    }
	} else if (!login.equals(other.login)) {
	    return false;
	}
	if (password == null) {
	    if (other.password != null) {
		return false;
	    }
	} else if (!password.equals(other.password)) {
	    return false;
	}
	if (role == null) {
	    if (other.role != null) {
		return false;
	    }
	} else if (!role.equals(other.role)) {
	    return false;
	}
	return true;
    }

    @Override
    public String getName() {
	return this.login;
    }

}
