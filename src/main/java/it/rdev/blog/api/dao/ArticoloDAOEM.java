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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.rdev.blog.api.controller.dto.ArticoloDTO.Stato;
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

	@Value("${default.page.size}")
	private int defaultPageSize;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/* Esegue una query generata a seconda dei parametri passati in input tramite la mappa. 
	 * 
	 * @param params 	mappa per l'applicazione dei filtri, può contenere le seguenti chiavi:
	 * 
	 * testo: 			Ricerca articoli con un certo testo contenuto nel 
	 * 					titolo, nel sottotitolo o nel corpo.
	 *
	 * autore:			Ricerca articoli di un determinato autore.
	 * 
	 * categoria: 		Ricarca articoli di una determinata categoria.
	 * 
	 * tag:				Ricerca articoli che contengono un certo tag.
	 *
	 * stato:			Ricerca articoli con un determinato stato.
	 * 
	 * page:			Numero di pagina.
	 * 
	 * count: 			Numero elementi per pagina.
	 * 
	 * @param userId	Identificativo dell'utente loggato al sistema utilizzato per 
	 * 					gestire la restituzione di articoli in stato bozza. 
	 * 					Nel caso in cui l'accesso al db avvenga da parte di un 
	 * 					utente anonimo, è possibile impostarlo a null.
	 * 
	 * @return			la lista di articoli recupaerata dal database.
	 * 
	 * */
	public List<Articolo> find(Map<String, String> params, Long userId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Articolo> criteriaQuery = criteriaBuilder.createQuery(Articolo.class);
		Root<Articolo> art = criteriaQuery.from(Articolo.class);
		
		criteriaQuery.select(art);
		
		// Aggiungo i predicates alla query
		Predicate finalPredicate = criteriaBuilder.and(
				getPredicates(params, userId, criteriaBuilder, art).toArray(new Predicate[0]));

		criteriaQuery.where(finalPredicate);
		
		
		// ################# PAGING #################
		
		int pageSize;
		int pageNum;
		
		try {
			pageSize = Integer.parseInt(params.get("count"));
		} catch (NumberFormatException e) {
			// se il parametro passato non è presente oppure 
			// non è corretto allora imposto il valore di default
			pageSize = defaultPageSize;
		}
		
		try {
			pageNum = Integer.parseInt(params.get("page"));
		} catch (NumberFormatException e) {
			pageNum = 1;
		}
		
		TypedQuery<Articolo> q = entityManager.createQuery(criteriaQuery);
		q.setFirstResult((pageNum - 1) * pageSize );
	    q.setMaxResults(pageSize);
		return q.getResultList();
		
	}
	
	
	public long total(Map<String, String> params, Long userId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<Articolo> art = countQuery.from(Articolo.class);
		
		countQuery.select(criteriaBuilder
				  .count(art));
		
		// Aggiungo i predicates alla query
		Predicate finalPredicate = criteriaBuilder.and(
				getPredicates(params, userId, criteriaBuilder, art).toArray(new Predicate[0]));

		countQuery.where(finalPredicate);
		
		return entityManager.createQuery(countQuery)
				  .getSingleResult();

	}
	
	
	private List<Predicate> getPredicates(Map<String, String> params, 
											Long userId, 
											CriteriaBuilder criteriaBuilder, 
											Root<Articolo> art ) {
	
		// Lista di predicati da mettere in and tra loro
		List<Predicate> predicates = new ArrayList<Predicate>(); 
		
		// Se ho il parametro testo
		if (params.get("testo") != null) {
			
			// Devo aggiungere alla query la ricerca all'interno di titolo, sottotitolo e testo;
			Predicate pTitolo = criteriaBuilder.like(art.get(Articolo_.titolo), "%" + params.get("testo") + "%");
			Predicate pSottotitolo = criteriaBuilder.like(art.get(Articolo_.sottotitolo), "%" + params.get("testo") + "%");
			Predicate pTesto = criteriaBuilder.like(art.get(Articolo_.testo), "%" + params.get("testo") + "%");
			
			// Aggiungo in or
			predicates.add(criteriaBuilder.or(pTitolo, pSottotitolo, pTesto));
		
		}
		
		// Se ho il parametro categoria
		if (params.get("categoria") != null) {
			
			// Devo aggiungere alla query la ricerca per categoria
			Predicate pCategoria = criteriaBuilder.equal(art.get(Articolo_.categoria), params.get("categoria"));
			
			// Aggiungo il predicato
			predicates.add(pCategoria);
		
		}
		
		
		// Se ho il parametro autore
		if (params.get("autore") != null) {
			
			// Effettuo la Join con la tabella user in modo da poter
			// Fare la ricerca tramite username
			Join<Articolo, User> user = art.join(Articolo_.autore);
			
			// Devo aggiungere alla query la ricerca per categoria
			Predicate pAutore = criteriaBuilder.equal(user.get(User_.username), params.get("autore"));
			
			// Aggiungo il predicato
			predicates.add(pAutore);
		
		}
		
		// Se ho il parametro tag
		if (params.get("tag") != null) {
			

			// Effettuo la Join con la tabella tag
			Join<Articolo, Tag> tag = art.join(Articolo_.tags);
			
			// Devo aggiungere alla query la ricerca per categoria
			Predicate pAutore = criteriaBuilder.equal(tag.get(Tag_.tag), params.get("tag"));
			
			// Aggiungo il predicato
			predicates.add(pAutore);
		
		}
		
		// ################# GESTIONE STATO ARTICOLI ####################
		// TODO FARE DEI TEST SU RICHIESTE CHE RIGUARDANO LO STATO

		// inizializzo il predicate riguardante lo stato dell'articolo
		// che può essere bozza o pubblicato
		Predicate finalPredicateStato = null;
		
		// se l'utente è anonimo oppure lo stato è pubblicato, allora mostro solo articoli pubblici.
		if (userId == null || 
				Stato.pubblicato.getValore().equals(params.get("stato"))) {
			Predicate pStato = criteriaBuilder.equal(art.get(Articolo_.stato), Stato.pubblicato.getValore());
			finalPredicateStato = pStato;
		
		} else { // altrimenti ho un utente loggato
			
			// caso di utente collegato e lo stato è bozza
			// devo restituire solamente i suoi articoli in bozza
			if (Stato.bozza.getValore().equals(params.get("stato"))) {
		
				Predicate pStato = criteriaBuilder.equal(art.get(Articolo_.stato), params.get("stato"));
				Predicate pAutore = criteriaBuilder.equal(art.get(Articolo_.autore), userId);
				finalPredicateStato = criteriaBuilder.and(pStato, pAutore);
			
			// lo stato è diverso, allora posso restituire tutto
			} else {
				
				// devo mostrare sia gli articoli pubblicati che quelli in bozza dell'utente.
				Predicate pStatoBozza = criteriaBuilder.equal(art.get(Articolo_.stato), Stato.bozza.getValore());
				Predicate pAutore = criteriaBuilder.equal(art.get(Articolo_.autore), userId);

				Predicate pStatoPubblicato = criteriaBuilder.equal(art.get(Articolo_.stato), Stato.pubblicato.getValore());
				
				finalPredicateStato = criteriaBuilder.and(pStatoBozza, pAutore);
				finalPredicateStato = criteriaBuilder.or(finalPredicateStato, pStatoPubblicato);
			}
			
		}
			
		if (finalPredicateStato != null) {
			predicates.add(finalPredicateStato);
		}
	
		// ################# FINE GESTIONE STATO ARTICOLI ####################
		
		return predicates;

	}
	
}
