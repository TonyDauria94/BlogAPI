package it.rdev.blog.api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.User;

public interface ArticoloDao extends CrudRepository<Articolo, Integer> {
	
	Optional<Articolo> findById(long id);
	
	Optional<Articolo> findByIdAndStato(long id, String stato);
	
	List<Articolo> findByStato(String stato);
	
	/* Query che permette di selezionare tutti gli articoli con stato1 
	 * uniti ad una serie di altri articoli che hanno uno stato2 ed uno specifico autore.
	 * 
	 * inserento come parametri Stato.PUBBLICATO, Stato.BOZZA, 
	 * 
	 * */
	@Query("FROM Articolo a WHERE a.stato = 'pubblicato' or (a.stato = 'bozza' and a.autore = :autore)")
	List<Articolo> findAll(@Param("autore") User autore);
}
