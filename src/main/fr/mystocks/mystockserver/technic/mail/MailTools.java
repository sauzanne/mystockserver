package fr.mystocks.mystockserver.technic.mail;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Queue;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import fr.mystocks.mystockserver.technic.constant.TechnicalConstant;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.exceptions.FunctionalException;
import fr.mystocks.mystockserver.technic.properties.PropertiesTools;
import fr.mystocks.mystockserver.technic.string.StringControlTools;

/**
 * Gestion des mail
 */
@Component
public class MailTools {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private JavaMailSenderImpl javaMailSender;

	@Value("${mail.force.address}")
	private String force;

	@Value("${mail.sender}")
	private String sender;

	@Value("${mail.host}")
	private String host;

	@Value("${mail.port}")
	private Integer port;

	@Value("${mail.username}")
	private String userName;

	@Value("${mail.password}")
	private String password;

	@Value("${mail.smtp.auth}")
	private String smtpAuthentication;

	@Value("${mail.smtp.starttls.enable}")
	private Boolean startTls;

	private Queue<MailBean> mailQueue = Lists.newLinkedList();

	@Autowired
	private PropertiesTools propertiesTools;

	public static final String STMP_CONNECTION_PROBLEM = "Could not connect to SMTP host";
	public static final String UNKNOWN_SMTP_HOST = "Unknown SMTP host";

	/**
	 * @author sauzanne
	 * 
	 * @return
	 */
	private Properties getMailProperties() {

		Properties mailProperties = new Properties();
		mailProperties.setProperty("mail.smtp.auth", smtpAuthentication);
		mailProperties.setProperty("mail.smtp.starttls.enable", startTls.toString());
		return mailProperties;
	}

	/**
	 * @author sauzanne @return the mailSender
	 */
	public JavaMailSender getJavaMailSender() {
		if (javaMailSender == null) {
			javaMailSender = new JavaMailSenderImpl();
			javaMailSender.setHost(host);
			javaMailSender.setPort(port);
			javaMailSender.setUsername(userName);
			javaMailSender.setPassword(password);
			javaMailSender.setDefaultEncoding(TechnicalConstant.ENCODING);

			javaMailSender.setJavaMailProperties(getMailProperties());

		}

		return javaMailSender;
	}

	/**
	 * Envoie d'un mail à une adresse mail
	 * 
	 * @param to
	 *            Le destinataire de l'email
	 * @param from
	 *            L'origine de l'email
	 * @param subject
	 *            Le sujet
	 * @param bodyText
	 *            Le texte
	 * @param files
	 *            Les fichiers joints
	 * @param html
	 *            Spécifie que le format du message est html
	 * @param exception
	 *            [option] préciser true si l'exception doit être levée
	 * @throws TechniqueException
	 */
	public void sendMessage(String to, String from, String subject, String bodyText, Map<String, File> files,
			boolean html, Boolean... exception) {
		String destinataire = Strings.isNullOrEmpty(force) ? to : force;
		String msgError = "Erreur lors de l'envoie de mail : " + subject + " à " + destinataire
				+ " \n\terror message : ";
		Boolean throwConditions = exception != null && exception.length > 0 && exception[0];
		if (StringControlTools.isFormatMail(to)) {
			MimeMessage message = getJavaMailSender().createMimeMessage();
			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(message, true);
				helper.setSubject(subject);
				helper.setFrom(!Strings.isNullOrEmpty(from) ? from : sender);
				helper.setTo(destinataire);
				logger.info(destinataire);

				StringBuilder messageTermine = new StringBuilder(bodyText);
				String saut = (BooleanUtils.isTrue(html)) ? "<br/>" : "\n";
				messageTermine.append(saut).append(saut);
				messageTermine.append(propertiesTools.getProperty("common.mail.noreply1")).append(saut);
				messageTermine.append(propertiesTools.getProperty("common.mail.noreply2")).append(saut);
				messageTermine.append(propertiesTools.getProperty("common.mail.noreply3")).append(saut);

				helper.setText(messageTermine.toString(), html);
				if (files != null) {
					for (Entry<String, File> file : files.entrySet()) {
						helper.addAttachment(file.getKey(), file.getValue());
					}
				}
			} catch (MessagingException e) {
				logger.error(originExceptionMessage(msgError, e));
			} catch (Exception e) {
				logger.error(originExceptionMessage(msgError, e));
				if (throwConditions) {
					ExceptionTools.processException(this, logger, e);
				}
			}
			try {
				this.getJavaMailSender().send(message);
			} catch (Exception e) {
				manageException(e, msgError, to, from, subject, bodyText, files, null, false, html);
				if (throwConditions) {
					ExceptionTools.processException(this, logger, e);
				}
			}
		} else {
			if (throwConditions) {
				throw new FunctionalException(this, "error.format.bad", new String[] { "to" });
			}
		}
	}


	/**
	 * Envoie d'un mail à une adresse mail
	 * 
	 * @param to
	 *            Le destinataire de l'email
	 * @param from
	 *            L'origine de l'email
	 * @param subject
	 *            Le sujet
	 * @param bodyText
	 *            Le texte
	 * @param attachment
	 *            Le nom du fichier joint
	 * @param byteArray
	 *            Les données joints
	 * @param html
	 *            Spécifie que le format du message est html
	 * @param exception
	 *            [option] préciser true si l'exception doit être levée
	 * @throws TechniqueException
	 */
	public void sendMessageByteArray(String to, String from, String subject, String bodyText, boolean html,
			Map<String, byte[]> files, Boolean... exception) {
		String destinataire = Strings.isNullOrEmpty(force) ? to : force;
		String msgError = "Erreur lors de l'envoie de mail : " + subject + " à " + destinataire
				+ " \n\terror message : ";
		Boolean throwConditions = exception != null && exception.length > 0 && exception[0];
		if (StringControlTools.isFormatMail(to)) {
			MimeMessage message = getJavaMailSender().createMimeMessage();
			MimeMessageHelper helper;
			try {
				helper = new MimeMessageHelper(message, true);
				helper.setSubject(subject);
				helper.setFrom(Strings.isNullOrEmpty(from) ? from : sender);
				helper.setTo(destinataire);
				logger.info(destinataire);

				StringBuilder messageTermine = new StringBuilder(bodyText);
				String saut = (BooleanUtils.isTrue(html)) ? "<br/>" : "\n";
				messageTermine.append(saut).append(saut);
				messageTermine.append(propertiesTools.getProperty("common.mail.noreply1")).append(saut);
				messageTermine.append(propertiesTools.getProperty("common.mail.noreply2")).append(saut);
				messageTermine.append(propertiesTools.getProperty("common.mail.noreply3")).append(saut);

				helper.setText(messageTermine.toString(), html);

				if (files != null) {
					for (Entry<String, byte[]> file : files.entrySet()) {
						helper.addAttachment(file.getKey(), new ByteArrayResource(file.getValue()));
					}
				}

			} catch (MessagingException e) {
				logger.error(originExceptionMessage(msgError, e));
			} catch (Exception e) {
				logger.error(originExceptionMessage(msgError, e));
				if (throwConditions) {
					ExceptionTools.processException(this, logger, e);
				}
			}
			try {
				this.getJavaMailSender().send(message);
			} catch (Exception e) {
				manageException(e, msgError, to, from, subject, bodyText, null, null, true, html);
				if (throwConditions) {
					ExceptionTools.processException(this, logger, e);
				}
			}
		} else {
			if (throwConditions) {
				throw new FunctionalException(this, "error.format.bad", new String[] { "to" });
			}
		}
	}

	/**
	 * Construit un message comportant l'exception d'origine
	 * 
	 * @param msgError
	 *            le message d'erreur
	 * @param e
	 *            l'exception
	 * @return un message d'erreur
	 */
	private String originExceptionMessage(String msgError, Exception e) {
		return msgError + e.getMessage() + "\ntrace de l'exeption d'origine :\n" + ExceptionTools.parseTrace(e);
	}

	/**
	 * Gestionnaire centralisé des exceptions
	 * 
	 * @param e
	 *            l'exception
	 * @param msgError
	 *            le message d'erreur
	 * @param to
	 *            le destinataire
	 * @param from
	 *            l'envoyeur
	 * @param subject
	 *            le sujet
	 * @param bodyText
	 *            le corps
	 * @param files
	 *            les fichiers
	 * @param byteArray
	 *            les donnÃ©es en format byte array
	 * @param nomFichier
	 *            le nom du fichier ci joint
	 * @param isDonneesJointsByteArray
	 *            les donnÃ©es en format byte array?
	 * @param html
	 *            contenu html ?
	 */
	private void manageException(Exception e, String msgError, String to, String from, String subject, String bodyText,
			Map<String, File> files, Map<String, byte[]> filesByteArray, boolean isDonneesJointsByteArray,
			boolean html) {
		if (e.getMessage().contains(STMP_CONNECTION_PROBLEM) || e.getMessage().contains(UNKNOWN_SMTP_HOST)) {
			synchronized (mailQueue) {
				MailBean mailBean = new MailBean(to, from, subject, bodyText, html, files, filesByteArray,
						isDonneesJointsByteArray);
				mailQueue.add(mailBean);
			}
		} else {
			logger.error(originExceptionMessage(msgError, e));
		}

	}

	/**
	 * Envoie d'un mail à une adresse mail
	 * 
	 * @param email
	 *            l'adresse mail
	 * @param subject
	 *            le sujet
	 * @param bodyText
	 *            le texte
	 */
	public void sendMessage(String email, String subject, String bodyText) {
		sendMessage(email, null, subject, bodyText, null, false);
	}

	/** . @param mailForcer the mailForcer to set */
	public void setMailForcer(String mailForcer) {
		this.force = mailForcer;
	}

	/** . @return the mailForcer */
	public String getMailForcer() {
		return force;
	}

	/** . @return the mailQueue */
	public Queue<MailBean> getMailQueue() {
		return mailQueue;
	}

	/** . @param mailQueue the mailQueue to set */
	public void setMailQueue(Queue<MailBean> mailQueue) {
		this.mailQueue = mailQueue;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

}
