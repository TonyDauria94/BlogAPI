package it.rdev.blog.api.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.User;

public interface ArticoloDAO extends CrudRepository<Articolo, Long> {
	
	Optional<Articolo> findById(long id);
	
	Optional<Articolo> findByIdAndStato(long id, String stato);
	
	List<Articolo> findByStato(String stato);
	
	/* Query che permette di selezionare tutti gli articoli pubblicati
	 * uniti a tutti gli articoli in bozza ed uno specifico autore.*/
	
	@Query("FROM Articolo a WHERE a.stato = 'pubblicato' or (a.stato = 'bozza' and a.autore = :autore)")
	List<Articolo> findAll(@Param("autore") User autore);
	
	/* Delete che elimina l'articolo facedno un controllo anche sull'id dell'autore */
	@Transactional
	@Modifying
	@Query("DELETE FROM Articolo a WHERE a.id = :idArticolo and a.autore = :autore")
	int deleteByIdAndAutore(long idArticolo, User autore);
	
}
