package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.exception.ResourceNotFoundException;
import it.rdev.blog.api.service.TagDetailsService;

/* Controller per la gestione dei tag.
 * Il servizio è accessibile dall'endpoint /api/tag.
 * */
@RestController
@RequestMapping("/api/tag")
public class TagAPIController {

	@Autowired
	private TagDetailsService tagService;

	/* @return Tutti i tag presenti sul database. 
	 * @throws ResourceNotFoundException se nel db non sono presenti tag.
	 * */
	@RequestMapping(path = "", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<TagDTO> get() {
		
		List<TagDTO> list = tagService.getAll();
		
		if(list.isEmpty()) 
			throw new ResourceNotFoundException("Non sono presenti tag.");
		
		return list;
	}

}
