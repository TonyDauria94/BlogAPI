package it.rdev.blog.api;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import it.rdev.blog.api.dao.CategoriaDAO;
import it.rdev.blog.api.dao.entity.Categoria;

@SpringBootTest(webEnvironment=RANDOM_PORT)
@DisplayName("<= ArticoloApiIntegration Test =>")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticoloApiIntegrationTests {

	@Autowired
	private WebTestClient client;
	
	@Autowired
	private CategoriaDAO categoriaDao;

	private String tokenTony;
	private String tokenDani;
	
	// Prima di tutto registro due utenti e ne recupero i token.
	// inizializzo il db una sola volta ad inizio test.
	@BeforeAll
	@Sql({"/database_init.sql"})
    public void setup() { 
		
		// registro alcuni utenti alla piattaforma
		register("tony", "passtony");
		register("dani", "passdani");

		// effettuo l'accesso e recupero i token
		tokenTony = authenticate("tony", "passtony");
		tokenDani = authenticate("dani", "passdani");

		
		// aggiungo qualche categoria al sistema
		Categoria c = new Categoria();
		c.setCategoria("cat1");
		categoriaDao.save(c);

		c.setCategoria("cat2");
		categoriaDao.save(c);
		
		c.setCategoria("cat3");
		categoriaDao.save(c);
		
		
		for (int i = 1; i<11; i++) {
			client.post().uri("/api/articolo")
			.header("Authorization", "Bearer " + tokenTony)
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
		.header("Authorization", "Bearer " + tokenTony)
		.exchange()
		.expectStatus().isOk();		
	}
	
	
	@Test
	@DisplayName("testo chiamata GET con filtro testo < 3 all'endpoint api/articolo con autenticazione")
	void testFilterTestoLessThan3Auth() {
		
		// inserisco un testo minore di tre caratteri
		client.get().uri("/api/articolo?testo=av")
		.accept(MediaType.APPLICATION_JSON)
		.header("Authorization", "Bearer " + tokenTony)
		.exchange()
		.expectStatus().isBadRequest();		
	}
	
	@Test
	@DisplayName("testo chiamata GET con filtro testo = 3 all'endpoint api/articolo con autenticazione")
	void testFilterTestoEquals3Auth() {
		
		// inserisco un testo minore di tre caratteri
		client.get().uri("/api/articolo?testo=tit")
		.accept(MediaType.APPLICATION_JSON)
		.header("Authorization", "Bearer " + tokenTony)
		.exchange()
		.expectStatus().isOk();		
	}
	
	@Test
	@DisplayName("testo chiamata GET con filtro testo senza risorse che matchano nel db"
			+ "all'endpoint api/articolo con autenticazione")
	void testFilterTestoAuth() {
		
		// inserisco un testo minore di tre caratteri
		client.get().uri("/api/articolo?testo=fsdfsad")
		.accept(MediaType.APPLICATION_JSON)
		.header("Authorization", "Bearer " + tokenTony)
		.exchange()
		.expectStatus().isNotFound();		
	}
	
	@Test
	@DisplayName("testo chiamata GET all'endpoint api/articolo/id articolo presente")
	void testGETByIDFound() {
		
		// trova le bozze dell'utente tony
		client.get().uri("/api/articolo/1")
		.accept(MediaType.APPLICATION_JSON)
		.header("Authorization", "Bearer " + tokenTony)
		.exchange()
		.expectStatus().isOk();		
	}
	
	@Test
	@DisplayName("testo chiamata GET all'endpoint api/articolo/id articolo inesistente")
	void testGETByIDNotFound() {
		
		// trova le bozze dell'utente tony
		client.get().uri("/api/articolo/3243")
		.accept(MediaType.APPLICATION_JSON)
		.header("Authorization", "Bearer " + tokenTony)
		.exchange()
		.expectStatus().isNotFound();		
	}
	
	@Test
	@DisplayName("test chiamata DELETE all'endpoint api/articolo/[id] senza autenticazione")
	void testDELETENoAuth() {
		// utente non loggato, dovrebbe non avere l'autorizzazione 
		// per cancellare l'articolo.
		client.delete().uri("/api/articolo/1")
		.exchange()
		.expectStatus().isUnauthorized();		
	}
	
	@Test
	@DisplayName("test chiamata DELETE all'endpoint api/articolo/[id] autenticato, senza permessi")
	void testDELETEUtenteSenzaDiritti() {
		// l' utente loggato non è l'autore dell'articolo
		client.delete().uri("/api/articolo/2")
		.header("Authorization", "Bearer " + tokenDani)
		.exchange()
		.expectStatus().isForbidden();		
	}

	
	@Test
	@DisplayName("test chiamata DELETE all'endpoint api/articolo/[id] autenticato, con permessi")
	void testDELETEUtenteAutore() {
		// l' utente loggato è l'autore dell'articolo
		client.delete().uri("/api/articolo/3")
		.header("Authorization", "Bearer " + tokenTony)
		.exchange()
		.expectStatus().isNoContent();		
	}

	
	// Metodo di servizio che registra un utente al sistema.
	private void register(String username, String password) {
		// registro un utente al sistema
		client.post().uri("/register")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue("{ \"username\": \"" + username + 
				"\", \"password\": \""+ password +"\" }")
		.exchange().expectStatus().isOk();
	}
	
	// Metodo di servizio che effettua l'accesso al sistema dati nome utente e pass.
	// @return token
	private String authenticate(String username, String password) {
		// recupero il token
		byte[] response = client.post().uri("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue("{ \"username\": \"" + username + 
						"\", \"password\": \""+ password +"\" }")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.token").exists()
				.returnResult().getResponseBodyContent();
			
		String token = null;
		String textResp = new String(response);
		int lastPos = textResp.lastIndexOf("\"");
		if(lastPos >= 0) {
			textResp = textResp.substring(0, lastPos);
			lastPos = textResp.lastIndexOf("\"");
			if(lastPos >= 0) {
				token = textResp.substring(lastPos + 1);
			}
		}
		return token;
	}
}
