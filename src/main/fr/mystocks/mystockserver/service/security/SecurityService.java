package fr.mystocks.mystockserver.service.security;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import fr.mystocks.mystockserver.data.security.Application;
import fr.mystocks.mystockserver.data.security.Session;
import fr.mystocks.mystockserver.data.security.User;

public interface SecurityService {

    User authenticate(String user, String password) throws NoSuchAlgorithmException;

    String getToken(User user);

    Session getSessionFromToken(String token);

    List<Application> getAllowedApplicationForUser(User user);

	/**
	 * Encryp a data with SHA-512
	 * @param dataToEncrypt the data to encrypt
	 * @return the encrypted data
	 * @throws NoSuchAlgorithmException
	 */
	String encryptInSHA(String dataToEncrypt) throws NoSuchAlgorithmException;

	String generateToken();

}
