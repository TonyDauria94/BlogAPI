package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.service.BlogCategoriaDetailsService;

@RestController
public class CategoriaController {
	
	@Autowired
	private BlogCategoriaDetailsService categoryService;
	
	@RequestMapping(path = "/api/categoria", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<CategoriaDTO> get() {
		
		List<CategoriaDTO> list = categoryService.findAll();
		
		if(list.isEmpty()) 
			throw new RuntimeException("Non sono presenti categorie.");
		
		return list;
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String handleRuntimeException(RuntimeException ex) {
		return ex.getMessage();
	}
	
}
