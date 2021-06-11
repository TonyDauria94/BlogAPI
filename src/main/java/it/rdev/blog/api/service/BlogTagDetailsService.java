package it.rdev.blog.api.service;

import java.util.List;

import it.rdev.blog.api.controller.dto.TagDTO;

public interface BlogTagDetailsService {

	public List<TagDTO> findAll();
	
}
