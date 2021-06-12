package it.rdev.blog.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.dao.ArticoloDao;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.service.BlogArticoloDetailsService;

@Service
public class BlogArticoloDetailsServiceImpl implements BlogArticoloDetailsService {

	@Autowired
	private ArticoloDao articoloDao;
	
	@Override
	public List<ArticoloDTO> findAll() {
		
		Iterable<Articolo> it = articoloDao.findAll();
		List<ArticoloDTO> list = new ArrayList<>();
		for (Articolo a : it) {
			list.add(toDto(a));
		}
		
		return list;
		
	}
	
	@Override
	public ArticoloDTO findById(long id) {

		Optional<Articolo> op = articoloDao.findById(id);
		
		// se l'optional non è vuoto, converti e restituisci.
		if (!op.isEmpty()) {
			return toDto(op.get());
		}
		
		// altrimenti restituisci null
		return null;
	}
	

	/*
	 * Converte in dto.
	 * */
	private ArticoloDTO toDto(Articolo a) {
		ArticoloDTO dto = new ArticoloDTO();
		dto.setId(a.getId());
		dto.setTitolo(a.getTitolo());
		dto.setSottotitolo(a.getSottotitolo());
		dto.setTesto(a.getTesto());
		dto.setDataPublicazione(a.getDataPublicazione());
		dto.setDataModifica(a.getDataModifica());
		dto.setDataCreazione(a.getDataCreazione());

		ArticoloDTO.Stato stato = null;
		
		switch (a.getStato()) {
		case "pubblicato":
			stato = ArticoloDTO.Stato.PUBBLICATO;
			break;
		case "bozza":
			stato = ArticoloDTO.Stato.BOZZA;
			break;
		}
		
		dto.setStato(stato);
		dto.setAutore(a.getAutore().getUsername());
		dto.setCategoria(a.getCategoria());
		
		List<String> tags = new ArrayList<>();
		
		for (Tag t : a.getTags()) {
			tags.add(t.getTag());
		}
		
		dto.setTags(tags);
		
		return dto;
	}

}
