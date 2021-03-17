package fr.mystocks.mystockserver.test.pkce;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import fr.mystocks.mystockserver.technic.configuration.start.SpringBootWebApplication;
import fr.mystocks.mystockserver.technic.crypto.PkceUtil;
import fr.mystocks.mystockserver.technic.exceptions.ExceptionTools;
import fr.mystocks.mystockserver.technic.mail.MailTools;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootWebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/config/application-test.properties")
public class PkceTest {

	@Autowired
	private MailTools mailTools;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void test() {
		try {

			String codeVerifier = PkceUtil.generateCodeVerifier();
			System.out.println("Code verifier = " + codeVerifier);

			String codeChallenge = PkceUtil.generateCodeChallange(codeVerifier);
			System.out.println("Code challenge = " + codeChallenge);

		} catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
			ExceptionTools.processExceptionOnlyWithLogging(this, logger, ex);
		}
	}

}