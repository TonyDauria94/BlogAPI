package it.rdev.blog.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.ArticoloDTO;
import it.rdev.blog.api.controller.dto.ArticoloDTO.Stato;
import it.rdev.blog.api.controller.dto.PageDTO;
import it.rdev.blog.api.controller.dto.UserDTO;
import it.rdev.blog.api.dao.ArticoloDAO;
import it.rdev.blog.api.dao.ArticoloDAOEM;
import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.dao.entity.User;
import it.rdev.blog.api.service.ArticoloDetailsService;

@Service
public class ArticoloDetailsServiceImpl implements ArticoloDetailsService {
	
	@Value("${default.page.size}")
	private int defaultPageSize;

	@Autowired
	private ArticoloDAO articoloDao;
	
	@Autowired
	private ArticoloDAOEM articoloDaoEm;

	@Override
	public void saveOrUpdate(ArticoloDTO articolo) {
		articoloDao.save(toEntity(articolo));
	}
	
	@Override
	public boolean delete(long id, long idAutore) {
		User u = new User();
		u.setId(idAutore);
		return articoloDao.deleteByIdAndAutore(id, u) > 0;
		
	}
	
	/* Restituisce tutti gli articoli */
	@Override
	public List<ArticoloDTO> getAll() {
		
		Iterable<Articolo> it = articoloDao.findAll();
		List<ArticoloDTO> list = new ArrayList<>();
		for (Articolo a : it) {
			list.add(toDto(a));
		}
		
		return list;
		
	}
	
	/* Restituisce un articolo tramite il suo identificativo.*/
	@Override
	public ArticoloDTO getById(long id) {

		Optional<Articolo> op = articoloDao.findById(id);
		
		// se l'optional non è vuoto, converti e restituisci.
		if (!op.isEmpty()) {
			return toDto(op.get());
		}
		
		// altrimenti restituisci null
		return null;
	}
	
	@Override
	public List<ArticoloDTO> getbyStato(Stato stato) {
		Iterable<Articolo> it = articoloDao.findByStato(stato.getValore());
		List<ArticoloDTO> list = new ArrayList<>();
		for (Articolo a : it) {
			list.add(toDto(a));
		}
		
		return list;
	}

	/* Restituisce tutti gli articoli pubblici e le bozze di uno specifico utente.
	 * */
	@Override
	public List<ArticoloDTO> getPubbliciAndBozze(long id_utente) {
		Iterable<Articolo> it = articoloDao.findAll(new User().setId(id_utente));
		List<ArticoloDTO> list = new ArrayList<>();
		for (Articolo a : it) {
			list.add(toDto(a));
		}
		
		return list;
	}
	
	@Override
	public PageDTO<ArticoloDTO> getByFilters(Map<String, String> filters, Long userId) {
		// conto il numero di risultati.
		long total = articoloDaoEm.total(filters, userId);
		
		// Se il numero di risultati è <= di 0, allora è inutile 
		// eseguire la query successiva
		if(total <= 0) {
			return null;
		}
		
		// Recupero gli articoli
		Iterable<Articolo> it = articoloDaoEm.find(filters, userId);
		
		// Converto gli articoli in DTO
		List<ArticoloDTO> list = new ArrayList<>();
		for (Articolo a : it) {
			list.add(toDto(a));
		}
		
		// Se la lista di elementi è vuota, allora non sono stati trovati elementi
		if(list.isEmpty()) {
			return null;
		}
		
		// Impostazioni pagina
		PageDTO<ArticoloDTO> page = new PageDTO<>();

		page.setContenuto(list);
		page.setTotaleRisultati(total);
		
		page.setPaginaCorrente(Integer.parseInt(filters.getOrDefault("page", "1")));
		
		
		int numeroElementiPerPagina = Integer.parseInt(filters.getOrDefault("count", String.valueOf(defaultPageSize)));
		
		// calcolo l'ultima pagina
		// faccio il casting a double, altrimenti il calcolo mi restituirebbe già un numero arrotondato.
		int ultimaPagina = (int) Math.ceil((double) total / (double) numeroElementiPerPagina);
		
		page.setUltimaPagina(ultimaPagina);
		
		return page;
	}
	
	@Override
	public PageDTO<ArticoloDTO> getByFilters(Map<String, String> filters) {
		// Richiamo il getByFilters settando a null l'id dell'utente
		// In modo che vengano mostrati solamente gli articoli pubblicati
		return getByFilters(filters, null);
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
			stato = ArticoloDTO.Stato.pubblicato;
			break;
		case "bozza":
			stato = ArticoloDTO.Stato.bozza;
			break;
		}
		
		dto.setStato(stato);
		
		UserDTO autore = new UserDTO();
		autore.setId(a.getAutore().getId());
		autore.setUsername(a.getAutore().getUsername());
		dto.setAutore(autore);
		
		dto.setCategoria(a.getCategoria());
		
		List<String> tags = new ArrayList<>();
		
		for (Tag t : a.getTags()) {
			tags.add(t.getTag());
		}
		
		dto.setTags(tags);
		
		return dto;
	}

	private Articolo toEntity(ArticoloDTO dto) {
		Articolo a = new Articolo();

		a.setId(dto.getId());
		a.setTitolo(dto.getTitolo());
		a.setSottotitolo(dto.getSottotitolo());
		a.setTesto(dto.getTesto());
		a.setDataPublicazione(dto.getDataPublicazione());
		a.setDataModifica(dto.getDataModifica());
		a.setDataCreazione(dto.getDataCreazione());
		a.setStato(dto.getStato());
		
		User u = new User();
		UserDTO udto = dto.getAutore();
		if (udto != null) {
			u.setId(udto.getId());
			u.setUsername(udto.getUsername());
			a.setAutore(u);
		}
		
		a.setCategoria(dto.getCategoria());
		
		List<Tag> tags = new ArrayList<>();
		
		if (dto.getTags() != null) {
			for (String s : dto.getTags()) {
				Tag t = new Tag();
				t.setTag(s);
				tags.add(t);
			}
		}
		
		a.setTags(tags);
		
		return a;
	}

}
