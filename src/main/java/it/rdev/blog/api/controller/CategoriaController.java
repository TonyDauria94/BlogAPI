package it.rdev.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.service.BlogCategoriaDetailsService;

@RestController
public class CategoriaController {
	
	@Autowired
	private BlogCategoriaDetailsService categoryService;
	
	@RequestMapping(path = "/api/categoria", method = RequestMethod.GET)
	public List<CategoriaDTO> get() {
		
		List<CategoriaDTO> list = categoryService.findAll();
		
		return list;
	}
	
	
}
