package it.rdev.blog.api.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.rdev.blog.api.dao.entity.Articolo;

public interface ArticoloDao extends CrudRepository<Articolo, Integer> {
	
	Optional<Articolo> findById(long id);
	
	Optional<Articolo> findByIdAndStato(long id, String stato);
	
	List<Articolo> findByStato(String stato);
	
	@Query("FROM Articolo a WHERE a.stato = :stato or (a.stato = :stato2 and autore = :autore)")
	List<Articolo> findProva(@Param("stato") String stato, 
			@Param("stato2") String stato2, 
			@Param("autore") String autore);
}
