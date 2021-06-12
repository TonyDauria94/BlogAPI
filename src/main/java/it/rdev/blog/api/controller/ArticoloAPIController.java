package it.rdev.blog.api.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.config.JwtTokenUtil;
import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.controller.dto.ArticoloDTO.Stato;
import it.rdev.blog.api.exception.ResourceNotFoundException;
import it.rdev.blog.api.service.BlogArticoloDetailsService;

/* Classe controller che permette l'accesso e la modifica degli articoli.
 * Il servizio è accessibile dall'endpoint /api/articolo.
 * */
@RestController
@RequestMapping("/api/articolo")
public class ArticoloAPIController {

	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private BlogArticoloDetailsService articoloService;
	
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<ArticoloDTO> get(
			@RequestHeader(required = false, value = "Authorization") String token) {
		
		List<ArticoloDTO> list= new ArrayList<>();
		
		// TODO dal momento che questa path non viene filtrata dall'autenticazione,
		// l'utente potrebbe avere un token scaduto, bisognerebbe aggiungere un
		// controllo sulla scadenza.
		
		// se l'utente ha un token
		if (token != null) {
			// recupero l'id dell'utente
			Long userId = jwtUtil.getUserIdFromToken(token);
			
			// recupero gli articoli pubblici e le bozze dell'utente.
			list = articoloService.getPubbliciAndBozze(userId);
			
		} else { // utente anonimo
			// Restituisco gli articoli che hanno lo stato pubblicato
			list = articoloService.getbyStato(Stato.pubblicato);
			
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
		
		// se l'utente ha un token e l'articolo è una bozza
		if (token != null && Stato.bozza.getValore().equals(articolo.getStato())) {
			// recupero lo username dell'utente
			String username = jwtUtil.getUsernameFromToken(token);
			
			// se lo username non coincide con quello dell'articolo
			// lancia una eccezione.
			if(!username.equals(articolo.getAutore())) {
				throw new ResourceNotFoundException("Articolo non trovato");
			}
			
		} 
		
		return articolo;
		
	}
	
	/* Inserisce un nuovo articolo */
	@RequestMapping(path = "", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void post (
			@RequestHeader(required = true, value = "Authorization") String token,
			@RequestBody ArticoloDTO articolo) {
		
		if (token != null) {
			// recupero lo username dell'utente
			Long id = jwtUtil.getUserIdFromToken(token);
			String username = jwtUtil.getUsernameFromToken(token);
			
			// imposto l'autore dell'articolo
			articolo.setAutoreId(id);
			articolo.setAutore(username);
			// imposto data creazione, data modifica.
			articolo.setDataCreazione(LocalDateTime.now());
			articolo.setDataModifica(LocalDateTime.now());
			
			// imposto lo stato a bozza.
			articolo.setStato(Stato.bozza);
			
			// effettuo la insert.
			articoloService.post(articolo);
		} 
		
	}
	
	// Gestisce eccezioni lanciate quando vengono inseriti dati errati.
	@ExceptionHandler({SQLIntegrityConstraintViolationException.class, PropertyValueException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public String handleSQLIntegrityConstraintViolationException(Exception ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String handleRuntimeException(ResourceNotFoundException ex) {
		return ex.getMessage();
	}
	
}
