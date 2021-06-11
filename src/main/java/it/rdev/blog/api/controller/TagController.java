package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.service.BlogTagDetailsService;

@RestController
public class TagController {

	@Autowired
	private BlogTagDetailsService tagService;

	@RequestMapping(path = "/api/tag", method = RequestMethod.GET)
	public List<TagDTO> get() {
		
		List<TagDTO> list = tagService.findAll();
		
		return list;
	}
	
}
