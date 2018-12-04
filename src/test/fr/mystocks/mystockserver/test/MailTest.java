package fr.mystocks.mystockserver.test;

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
import fr.mystocks.mystockserver.technic.mail.MailTools;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootWebApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/config/application-test.properties")
public class MailTest {

	@Autowired
	private MailTools mailTools;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void test() {
		mailTools.sendMessage("sauzanne@gmail.com", "test mystockserver", "ceci est un test !");
	}

}