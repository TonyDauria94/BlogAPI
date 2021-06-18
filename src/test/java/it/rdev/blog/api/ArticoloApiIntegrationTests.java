package it.rdev.blog.api;

import static org.mockito.Mockito.never;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import it.rdev.blog.api.dao.CategoriaDAO;
import it.rdev.blog.api.dao.entity.Categoria;

@SpringBootTest(webEnvironment=RANDOM_PORT)
@DisplayName("<= ArticoloApiIntegration Test =>")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticoloApiIntegrationTests extends TestDbInit {

	@Autowired
	private WebTestClient client;
	
	@Autowired
	private CategoriaDAO categoriaDao;
	
	private String token;
	
	// Prima di tutto registro un utente e ne recupero il token
	@BeforeAll
    public void setup() { 
		// registro un utente al sistema
		client.post().uri("/register")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue("{ \"username\": \"tony\", \"password\": \"password1\" }")
		.exchange();
		
		// recupero il token
		byte[] response = client.post().uri("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"username\": \"tony\", \"password\": \"password1\" }")
				.exchange()
				.expectBody()
				.jsonPath("$.token").exists()
				.returnResult().getResponseBodyContent();
			
		String textResp = new String(response);
		int lastPos = textResp.lastIndexOf("\"");
		if(lastPos >= 0) {
			textResp = textResp.substring(0, lastPos);
			lastPos = textResp.lastIndexOf("\"");
			if(lastPos >= 0) {
				token = textResp.substring(lastPos + 1);
			}
		}
		
		Categoria c = new Categoria();
		c.setCategoria("cat1");
		categoriaDao.save(c);

		c.setCategoria("cat2");
		categoriaDao.save(c);
		
		c.setCategoria("cat3");
		categoriaDao.save(c);
		
		
		for (int i = 1; i<11; i++) {
			client.post().uri("/api/articolo")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("{ \"titolo\": \"articolo"+ i +"\", "
					+ "\"sottotitolo\": \"sottotitolo"+ i +"\", "
					+ "\"testo\": \"testo dell'articolo numero " + i + "\", "
					+ "\"categoria\": \"cat2\"}")
			.exchange().expectStatus().isNoContent();
			}
	}
	
	
	public ArticoloApiIntegrationTests() { }
	
	@Test
	@DisplayName("testo chiamata GET all'endpoint api/articolo senza autenticazione")
	void testGETNoAuth() {
		// Non trova niente, sul db ci sono solo bozze
		client.get().uri("/api/articolo")
		.exchange()
		.expectStatus().isNotFound();		
	}
	
	@Test
	@DisplayName("testo chiamata GET all'endpoint api/articolo con autenticazione")
	void testGETAuth() {
		
		// trova le bozze dell'utente tony
		client.get().uri("/api/articolo")
		.accept(MediaType.APPLICATION_JSON)
		.header("Authorization", "Bearer " + token)
		.exchange()
		.expectStatus().isOk();		
	}
	
	
}
