package it.rdev.blog.api;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import it.rdev.blog.api.dao.TagDAO;
import it.rdev.blog.api.dao.entity.Tag;

@SpringBootTest(webEnvironment=RANDOM_PORT)
@DisplayName("<= TagApiIntegration Test =>")
public class TagApiIntegrationTests extends TestDbInit {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebTestClient client;
	
	@Autowired
	private TagDAO tagDAO;
	
	@BeforeAll
    public static void setup() { }
	
	private TagApiIntegrationTests() {}
	
	@Test
	@DisplayName("testo chiamata GET all'endpoint api/tag con db vuoto")
	void testGETVuoto() {
		client.get().uri("/api/tag")
		.exchange()
		.expectStatus().isNotFound();
				
	}
	
	@Test
	@DisplayName("testo chiamata GET all'endpoint api/tag con db pieno")
	void testGETPieno() {
		
		Tag t = new Tag();
		t.setTag("prova");
		
		tagDAO.save(t);
		
		client.get().uri("/api/tag")
		.exchange()
		.expectStatus().isOk();

		tagDAO.delete(t);
		
	}
	
}
