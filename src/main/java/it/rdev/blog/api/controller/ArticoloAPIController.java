package it.rdev.blog.api.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import it.rdev.blog.api.config.JwtTokenUtil;
import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.controller.dto.ArticoloDTO.Stato;
import it.rdev.blog.api.controller.dto.ExceptionDTO;
import it.rdev.blog.api.exception.NotTheAuthorException;
import it.rdev.blog.api.exception.ResourceNotFoundException;
import it.rdev.blog.api.service.ArticoloDetailsService;

/* Classe controller che permette l'accesso e la modifica degli articoli.
 * Il servizio è accessibile dall'endpoint /api/articolo.
 * */
@RestController
@RequestMapping("/api/articolo")
public class ArticoloAPIController {

	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private ArticoloDetailsService articoloService;

		
	@RequestMapping(path = "", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<ArticoloDTO> get(
			@RequestHeader(required = false, value = "Authorization") String token,
			@RequestParam(required = false) Map<String, String> params) {
		
		List<ArticoloDTO> list;
		
		// Se il parametro testo ha meno di 3 caratteri, allora lancio una eccezione
		if (params.get("testo") != null && params.get("testo").length() < 3)
			throw new IllegalArgumentException("Inserisci almeno tre caratteri per fare la ricerca sul testo.");
		
		// se l'utente ha un token
		if (token != null) {
			// recupero l'id dell'utente
			Long userId = jwtUtil.getUserIdFromToken(token);
					
			// Effettuo la query utilizzando i filtri passati nella queryStrig
			// passo al metodo anche l'id dell'utente loggato, in modo che
			// possa restituirgli anche eventuali suoi articoli personali in stato bozza
			list = articoloService.getByFilters(params, userId);
			
		} else { // utente anonimo
			
			// Effettuo la query in modalità utente anonimo.
			list = articoloService.getByFilters(params);
			
		}
		
		// Se la lista è vuota lancio una ResourceNotFoundException.
		if(list.isEmpty()) 
			throw new ResourceNotFoundException("Non sono presenti articoli.");
		
		return list;
	}
	
	/* Restituisce un articolo dato il suo id. */
	@RequestMapping(path = "/{id:\\d+}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ArticoloDTO getById(
			@RequestHeader(required = false, value = "Authorization") String token,
			@PathVariable Integer id) {
		
		// recupero l'articolo;
		ArticoloDTO articolo = articoloService.getById(id);
		
		// se l'articolo non esiste lancio un'eccezione
		if(articolo == null) 
			throw new ResourceNotFoundException("Articolo non trovato");
		
		// Se l'articolo è una bozza
		if (Stato.bozza.getValore().equals(articolo.getStato())) {
			
			// se l'utente ha un token
			if (token != null) {
				// recupero lo username dell'utente
				String username = jwtUtil.getUsernameFromToken(token);
				
				// se lo username non coincide con quello dell'articolo
				// lancia una eccezione.
				if(!username.equals(articolo.getAutore())) {
					throw new ResourceNotFoundException("Articolo non trovato");
				}
			} else { // l'utente non ha un token
				throw new ResourceNotFoundException("Articolo non trovato");
			}
			
		}
		
		return articolo;
		
	}
	
	/* Inserisce un nuovo articolo */
	@RequestMapping(path = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void post (
			@RequestHeader(required = true, value = "Authorization") String token,
			@RequestBody ArticoloDTO articolo) {
		
		if (token != null) {
			// recupero lo username dell'utente
			Long id = jwtUtil.getUserIdFromToken(token);
			String username = jwtUtil.getUsernameFromToken(token);
			
			// imposto l'autore dell'articolo.
			// Questo sovrascrive anche eventuali nomi di autori inseriti
			// in input dall'utente, rendendo inpossibile l'aggiunta di un articolo
			// con un nome utente diverso da quello dell'utente loggato.
			articolo.setAutoreId(id);
			articolo.setAutore(username);
			
			// imposto data creazione, data modifica.
			// Trattandosi di un nuovo articolo, le imposto alla data attuale.
			articolo.setDataCreazione(LocalDateTime.now());
			articolo.setDataModifica(LocalDateTime.now());
			
			// imposto lo stato a bozza.
			articolo.setStato(Stato.bozza);
			
			// effettuo la insert.
			articoloService.saveOrUpdate(articolo);
		} 
		
	}
	
	
	//TODO Fare altri test al metodo PUT
	/* Modifica un articolo. */
	@RequestMapping(path = "/{idArticolo:\\d+}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void put (
			@RequestHeader(required = true, value = "Authorization") String token,
			@PathVariable Integer idArticolo,
			@RequestBody final ArticoloDTO articolo) {
		
		if (token != null) {
			// recupero l'id dell'utente
			Long id = jwtUtil.getUserIdFromToken(token);
			
			// Recupero l'articolo dal db
			ArticoloDTO articoloDb = articoloService.getById(idArticolo);
			
			// se l'articolo è null lancio l'eccezione
			if (articoloDb == null) throw new ResourceNotFoundException("Articolo non trovato");
			
			// se l'articolo ha un autore differente dall'utente loggato
			if (articoloDb.getAutoreId() != id)
				throw new NotTheAuthorException("Gli articoli possono essere modificati"
						+ " solamente dal proprio autore");
		
			if(articolo.getTitolo() != null)
				articoloDb.setTitolo(articolo.getTitolo());
			
			if(articolo.getSottotitolo() != null)
				articoloDb.setSottotitolo(articolo.getSottotitolo());
			
			if(articolo.getTesto() != null)
				articoloDb.setTesto(articolo.getTesto());
			
			// aggiorno la data di modifica.
			articoloDb.setDataModifica(LocalDateTime.now());
		
			// se l'articolo sul db è in bozza e l'articolo passato è pubblicato
			if (Stato.bozza.getValore().equals(articoloDb.getStato())
			 && Stato.pubblicato.getValore().equals(articolo.getStato()) ) {
				// allora posso impostare la data di pubblicazione
				articoloDb.setDataCreazione(LocalDateTime.now());
				articoloDb.setStato(Stato.pubblicato);
			}
			
			if(articolo.getCategoria() != null)
			articoloDb.setCategoria(articolo.getCategoria());
			
			if(articolo.getTags() != null)
			articoloDb.setTags(articolo.getTags());
			
			// effettuo la insert.
			articoloService.saveOrUpdate(articoloDb);
		} 
		
	}
	
	
	
	/* Elimina un articolo */
	@RequestMapping(path = "/{id:\\d+}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete (
			@RequestHeader(required = true, value = "Authorization") String token,
			@PathVariable Integer id) {
		
		if (token != null) {
			Long userId = jwtUtil.getUserIdFromToken(token);
			
			// Recupero l'articolo
			ArticoloDTO articolo = articoloService.getById(id);
			
			// se l'articolo è null lancio l'eccezione
			if (articolo == null) throw new ResourceNotFoundException("Articolo non trovato");
			
			// se l'autore dell'articolo non è l'utente loggato, lancio l'eccezione
			if (articolo.getAutoreId() != userId) 
				throw new NotTheAuthorException("Gli articoli possono essere eliminati"
												+ " solo dal proprio autore");
			
			// Effettuo l'eliminazione
			articoloService.delete(articolo.getId(), userId);
		} 
		
		
	}
	
	/* Gestisce eccezioni lanciate quando vengono inseriti dati errati.*/
	@ExceptionHandler({SQLIntegrityConstraintViolationException.class, PropertyValueException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionDTO handleSQLIntegrityConstraintViolationException(Exception ex) {
		return new ExceptionDTO()
				.setEccezione(ex.getClass().getName())
				.setMessaggio(ex.getMessage());
	}
	
	/* Gestisce eccezioni lanciata quando si tenta di modificare un articolo di un altro autore.*/
	@ExceptionHandler()
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ExceptionDTO handleNotTheAuthorException(NotTheAuthorException ex) {
		return new ExceptionDTO()
				.setEccezione(ex.getClass().getName())
				.setMessaggio(ex.getMessage());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionDTO handleRuntimeException(ResourceNotFoundException ex) {
		return new ExceptionDTO()
				.setEccezione(ex.getClass().getName())
				.setMessaggio(ex.getMessage());
	}
	
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionDTO handleIllegalArgumentException(IllegalArgumentException ex) {
		return new ExceptionDTO()
				.setEccezione(ex.getClass().getName())
				.setMessaggio(ex.getMessage());
	}
	
	/* Gestisce eccezione lanciata quando viene utilizzato un token scaduto. */
	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionDTO handleExpiredJwtExceptionException(ExpiredJwtException ex) {
		return new ExceptionDTO()
				.setEccezione(ex.getClass().getName())
				.setMessaggio("Token scaduto!");
	}
	
}
