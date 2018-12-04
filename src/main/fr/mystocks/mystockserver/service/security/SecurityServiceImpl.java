package fr.mystocks.mystockserver.service.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mystocks.mystockserver.dao.security.ApplicationUserDao;
import fr.mystocks.mystockserver.dao.security.SessionDao;
import fr.mystocks.mystockserver.dao.security.UserDao;
import fr.mystocks.mystockserver.data.security.Application;
import fr.mystocks.mystockserver.data.security.ApplicationUser;
import fr.mystocks.mystockserver.data.security.Session;
import fr.mystocks.mystockserver.data.security.User;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;

@Transactional
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private UserDao<User> userDao;

	@Autowired
	private SessionDao<Session> sessionDao;

	@Autowired
	private ApplicationUserDao<ApplicationUser> applicationUserDao;

	private SecureRandom random = new SecureRandom();

	private final static long SESSION_LENGTH = 60;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public User authenticate(String user, String password) throws NoSuchAlgorithmException {
		return userDao.authenticate(user, encryptInSHA(password));
	}

	@Override
	public String encryptInSHA(String dataToEncrypt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(dataToEncrypt.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		// System.out.println("Hex format : " + sb.toString());

		// convert the byte to hex format method 2
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		// System.out.println("Hex format : " + hexString.toString()); }
		return hexString.toString();
	}

	@Override
	public String getToken(User user) {

		try {

			LocalDateTime now = LocalDateTime.now();

			/* on recherche une session existante */
			List<Session> sessions = sessionDao.findByUser(user);

			for (Session session : sessions) {
				if (session != null && session.getExpiry().isAfter(now)) {
					return session.getToken();
				} else {
					sessionDao.delete(session);
				}
			}

			Session session = new Session();

			session.setExpiry(LocalDateTime.now().plusMinutes(SESSION_LENGTH));

			session.setToken(generateToken());

			session.setUser(user);

			sessionDao.create(session);
			return session.getToken();
		} catch (RuntimeException e) {
			ExceptionTools.processException(this, logger, e);
		}
		return null;

	}
	@Override
	public String generateToken() {
		return new BigInteger(100, random).toString(32);
	}

	@Override
	public Session getSessionFromToken(String token) {
		Session session = sessionDao.findByToken(token);

		LocalDateTime now = LocalDateTime.now();

		if (session != null && session.getExpiry().isAfter(now)) {
			return session;
		}
		return null;
	}

	@Override
	public List<Application> getAllowedApplicationForUser(User user) {

		List<Application> listApplications = new ArrayList<>();

		List<ApplicationUser> listApplicationUser = applicationUserDao.findByUser(user);

		for (ApplicationUser applicationUser : listApplicationUser) {
			listApplications.add(applicationUser.getApplicationUserId().getApplication());
		}

		return listApplications;
	}

}
