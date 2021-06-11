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
import it.rdev.blog.api.service.BlogTagDetailsService;

@RestController
public class TagController {

	@Autowired
	private BlogTagDetailsService tagService;

	@RequestMapping(path = "/api/tag", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<TagDTO> get() {
		
		List<TagDTO> list = tagService.findAll();
		
		if(list.isEmpty()) 
			throw new RuntimeException("Non sono presenti tags.");
		
		return list;
	}

	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String handleRuntimeException(RuntimeException ex) {
		return ex.getMessage();
	}
}
