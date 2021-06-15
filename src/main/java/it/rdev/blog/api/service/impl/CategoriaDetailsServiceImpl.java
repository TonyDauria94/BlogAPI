package it.rdev.blog.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.CategoriaDTO;
import it.rdev.blog.api.dao.CategoriaDAO;
import it.rdev.blog.api.dao.entity.Categoria;
import it.rdev.blog.api.service.CategoriaDetailsService;

@Service
public class CategoriaDetailsServiceImpl implements CategoriaDetailsService {

	@Autowired
	private CategoriaDAO categoriaDao;
	
	@Override
	public List<CategoriaDTO> getAll() {
		
		Iterable<Categoria> it = categoriaDao.findAll();
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
