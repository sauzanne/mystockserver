package fr.mystocks.mystockserver.data.security;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mystockserver.application_user")
public class ApplicationUser {

	@EmbeddedId
	private ApplicationUserId applicationUserId;

	/**
	 * @return the applicationUserId
	 */
	public ApplicationUserId getApplicationUserId() {
		return applicationUserId;
	}

	/**
	 * @param applicationUserId
	 *            the applicationUserId to set
	 */
	public void setApplicationUserId(ApplicationUserId applicationUserId) {
		this.applicationUserId = applicationUserId;
	}

}
