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

import it.rdev.blog.api.dao.CategoriaDAO;
import it.rdev.blog.api.dao.entity.Categoria;

@SpringBootTest(webEnvironment=RANDOM_PORT)
@DisplayName("<= CategoriaApiIntegration Test =>")
public class CategoriaApiIntegrationTests extends TestDbInit {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebTestClient client;
	
	@Autowired
	private CategoriaDAO categoriaDAO;
	
	@BeforeAll
    public static void setup() { }
	
	private CategoriaApiIntegrationTests() {}
	
	@Test
	@DisplayName("testo chiamata GET all'endpoint api/categoria con db vuoto")
	void testGETVuoto() {
		client.get().uri("/api/categoria")
		.exchange()
		.expectStatus().isNotFound();		
	}
	
	@Test
	@DisplayName("testo chiamata GET all'endpoint api/categoria con db pieno")
	void testGETPieno() {
		
		Categoria c = new Categoria();
		c.setCategoria("prova");
		
		//aggiungo una categoria
		categoriaDAO.save(c);
		
		client.get().uri("/api/categoria")
		.exchange()
		.expectStatus().isOk();

		categoriaDAO.delete(c);
		
	}
	
}
