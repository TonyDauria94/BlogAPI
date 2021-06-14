package it.rdev.blog.api.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Articolo_;

@Component
public class ArticoloDAOEM {

	@PersistenceContext
	private EntityManager entityManager;
	
	/* Esegue una query generata a seconda dei parametri passati in input tramite la mappa. */
	public List<Articolo> find(Map<String, String> params) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Articolo> c = cb.createQuery(Articolo.class);
		Root<Articolo> art = c.from(Articolo.class);
		c.select(art);
		
		// Lista di predicati da mettere in and tra loro
		List<Predicate> predicates = new ArrayList<Predicate>(); 
		
		// Se ho il parametro testo
		if (params.get("testo") != null) {
			
			// Devo aggiungere alla query la ricerca all'interno di titolo, sottotitolo e testo;
			Predicate pTitolo = cb.like(art.get(Articolo_.titolo), "%" + params.get("testo") + "%");
			Predicate pSottotitolo = cb.like(art.get(Articolo_.sottotitolo), "%" + params.get("testo") + "%");
			Predicate pTesto = cb.like(art.get(Articolo_.testo), "%" + params.get("testo") + "%");
			
			// Aggiungo in or
			predicates.add(cb.or(pTitolo, pSottotitolo, pTesto));
		
		}
		
		
		// Se ho il parametro categoria
		if (params.get("categoria") != null) {
			
			// Devo aggiungere alla query la ricerca per categoria
			Predicate pCategoria = cb.equal(art.get(Articolo_.categoria), params.get("categoria"));
			
			// Aggiungo il predicato
			predicates.add(pCategoria);
		
		}
		
		// Aggiungo i predicates alla query
		Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[0]));
		c.where(finalPredicate);
		
		TypedQuery<Articolo> q = entityManager.createQuery(c);
		return q.getResultList();
		
	}
	
}
