package fr.mystocks.mystockserver.data.security;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ApplicationUserId implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6575172692511363397L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "server_user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "application_id", nullable = false)
	private Application application;
	
	/**
	     * @author sauzanne @return the user
	     */
	    public User getUser() {
	        return user;
	    }

	    /**
	     * @author sauzanne @param user the user to set
	     */
	    public void setUser(User user) {
	        this.user = user;
	    }

	    /**
	     * @author sauzanne @return the application
	     */
	    public Application getApplication() {
	        return application;
	    }

	    /**
	     * @author sauzanne @param application the application to set
	     */
	    public void setApplication(Application application) {
	        this.application = application;
	    }



}
