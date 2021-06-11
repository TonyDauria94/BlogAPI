package it.rdev.blog.api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.rdev.blog.api.dao.entity.Articolo;

public interface ArticoloDao extends CrudRepository<Articolo, Integer> {
	
	Optional<Articolo> findById(long id);
	
	Optional<Articolo> findByIdAndStato(long id, String stato);
	
	List<Articolo> fingByStato(String stato);
}
