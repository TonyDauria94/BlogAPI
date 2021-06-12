package it.rdev.blog.api.service;

import java.util.List;

import it.rdev.blog.api.controller.dto.ArticoloDTO;

public interface BlogArticoloDetailsService {

	public void post(ArticoloDTO articolo);	
	
	public List<ArticoloDTO> getAll();
	
	public ArticoloDTO getById(long id);
	
	public List<ArticoloDTO> getbyStato(ArticoloDTO.Stato stato);
	
	public List<ArticoloDTO> getPubbliciAndBozze(long id_utente);
	
}
