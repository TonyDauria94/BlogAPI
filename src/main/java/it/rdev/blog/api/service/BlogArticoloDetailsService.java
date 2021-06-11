package it.rdev.blog.api.service;

import java.util.List;

import it.rdev.blog.api.controller.dto.ArticoloDTO;

public interface BlogArticoloDetailsService {

	public List<ArticoloDTO> findAll();
	
	public ArticoloDTO findById(long id);
	
}
