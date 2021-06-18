package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.controller.dto.ExceptionDTO;
import it.rdev.blog.api.exception.ResourceNotFoundException;
import it.rdev.blog.api.service.CategoriaDetailsService;

/* Controller per la gestione delle categorie.
 * Il servizio è accessibile dall'endpoint /api/categoria.
 * */
@RestController
@RequestMapping("/api/categoria")
public class CategoriaAPIController {
	
	@Autowired
	private CategoriaDetailsService categoryService;
	
	/* @return Tutte le categorie presenti sul database.
	 * @throws ResourceNotFoundException se nel db non sono presenti categorie.
	 * */
	@RequestMapping(path = "", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<CategoriaDTO> get() {
		
		List<CategoriaDTO> list = categoryService.getAll();
		
		if(list.isEmpty()) 
			throw new ResourceNotFoundException("Non sono presenti categorie.");
		
		return list;
	}
	
	/* Gestisce la ResourceNotFoundException lanciata dal controller.
	 * nel caso in cui ciò accada, il controller invierà al client 
	 * una response con HttpStatus 404.
	 * */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionDTO handleRuntimeException(ResourceNotFoundException ex) {
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
