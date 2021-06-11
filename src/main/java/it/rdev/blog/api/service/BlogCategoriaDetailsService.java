package it.rdev.blog.api.service;

import java.util.List;

import it.rdev.blog.api.controller.dto.CategoriaDTO;

public interface BlogCategoriaDetailsService {

	public List<CategoriaDTO> findAll();
	
}
