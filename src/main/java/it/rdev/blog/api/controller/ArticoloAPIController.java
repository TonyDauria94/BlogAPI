package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.exception.ResourceNotFoundException;
import it.rdev.blog.api.service.BlogArticoloDetailsService;

@RestController
public class ArticoloAPIController {

	@Autowired
	private BlogArticoloDetailsService articoloService;
	
	@RequestMapping(path = "/api/articolo", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<ArticoloDTO> get() {
		
		List<ArticoloDTO> list = articoloService.findAll();
		
		if(list.isEmpty()) 
			throw new ResourceNotFoundException("Non sono presenti articoli.");
		
		return list;
	}
	
	
	@RequestMapping(path = "/api/articolo/{id:\\d+}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ArticoloDTO getById(@PathVariable Integer id) {
		
		ArticoloDTO articolo = articoloService.findById(id);
		
		if(articolo == null) 
			throw new ResourceNotFoundException("Articolo non trovato");
		
		return articolo;
		
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String handleRuntimeException(ResourceNotFoundException ex) {
		return ex.getMessage();
	}
	
}
