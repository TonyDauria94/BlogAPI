package it.rdev.blog.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.dao.CategoriaDao;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.service.BlogCategoriaDetailsService;

@Service
public class BlogCategoriaDetailsServiceImpl implements BlogCategoriaDetailsService {

	@Autowired
	private CategoriaDao cd;
	
	@Override
	public List<CategoriaDTO> findAll() {
		
		Iterable<Categoria> it = cd.findAll();
		List<CategoriaDTO> list = new ArrayList<>();
		for (Categoria c : it) {
			list.add(toDto(c)); // conversione in dto
		}
		
		return list;
		
	}
	
	/*
	 * Converte in dto.
	 * */
	private CategoriaDTO toDto(Categoria c) {
		CategoriaDTO dto = new CategoriaDTO();
		dto.setCategoria(c.getCategoria());
		return dto;
	}
	
}
