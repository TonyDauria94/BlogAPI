package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.exception.ResourceNotFoundException;
import it.rdev.blog.api.service.BlogTagDetailsService;

@RestController
public class TagAPIController {

	@Autowired
	private BlogTagDetailsService tagService;

	/* @return Tutti i tag presenti sul database. 
	 * @throws ResourceNotFoundException se nel db non sono presenti tag.
	 * */
	@RequestMapping(path = "/api/tag", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<TagDTO> get() {
		
		List<TagDTO> list = tagService.findAll();
		
		if(list.isEmpty()) 
			throw new ResourceNotFoundException("Non sono presenti tag.");
		
		return list;
	}

	
	/* Gestisce la ResourceNotFoundException lanciata dal controller.
	 * nel caso in ciò cui accada, il controller invierà al client 
	 * una response con HttpStatus 404.
	 * */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ex.getMessage();
	}
}
