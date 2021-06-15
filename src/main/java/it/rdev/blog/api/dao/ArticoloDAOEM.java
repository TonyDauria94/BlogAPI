package it.rdev.blog.api.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import it.rdev.blog.api.dao.entity.Articolo;
import it.rdev.blog.api.dao.entity.Articolo_;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.dao.entity.Tag_;
import it.rdev.blog.api.dao.entity.User;
import it.rdev.blog.api.dao.entity.User_;

/* Implementazioni di un dao per gli articoli che utilizza l'EntityManager
 * effettuare query sul db. */
@Component
public class ArticoloDAOEM {

	@PersistenceContext
	private EntityManager entityManager;
	
	/* Esegue una query generata a seconda dei parametri passati in input tramite la mappa. 
	 * 
	 * parametri della mappa:
	 * 
	 * testo: 		Ricerca articoli con un certo testo contenuto nel 
	 * 				titolo, nel sottotitolo o nel corpo.
	 *
	 * autore:		Ricerca articoli di un determinato autore.
	 * 
	 * categoria: 	Ricarca articoli di una determinata categoria.
	 * 
	 * tag:			Ricerca articoli che contengono un certo tag.
	 *
	 * stato:		Ricerca articoli con un determinato stato.
	 * 
	 * */
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
		
		
		// Se ho il parametro autore
		if (params.get("autore") != null) {
			
			// Effettuo la Join con la tabella user in modo da poter
			// Fare la ricerca tramite username
			Join<Articolo, User> user = art.join(Articolo_.autore);
			
			// Devo aggiungere alla query la ricerca per categoria
			Predicate pAutore = cb.equal(user.get(User_.username), params.get("autore"));
			
			// Aggiungo il predicato
			predicates.add(pAutore);
		
		}
		
		// Se ho il parametro tag
		if (params.get("tag") != null) {
			

			// Effettuo la Join con la tabella tag
			Join<Articolo, Tag> tag = art.join(Articolo_.tags);
			
			// Devo aggiungere alla query la ricerca per categoria
			Predicate pAutore = cb.equal(tag.get(Tag_.tag), params.get("tag"));
			
			// Aggiungo il predicato
			predicates.add(pAutore);
		
		}
		
		// Aggiungo i predicates alla query
		Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[0]));
		c.where(finalPredicate);
		
		TypedQuery<Articolo> q = entityManager.createQuery(c);
		return q.getResultList();
		
	}
	
}
