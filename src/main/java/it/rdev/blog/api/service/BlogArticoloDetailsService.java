package it.rdev.blog.api.service;

import java.util.List;

import it.rdev.blog.api.controller.dto.ArticoloDTO;

public interface BlogArticoloDetailsService {

	/* Aggiunge un articolo al database */
	public void post(ArticoloDTO articolo);
	
	/* Elimina un articolo dal database tramite l'id l'autore.
	 *
	 * @param 	id	 		Identificativo dell'articolo da eliminare.
	 * @param 	idAutore 	Identificativo dell'autore che ha scritto l'articolo. 
	 * @return 				true l'eleminazione ha avuto successo, false altrimenti. */
	public boolean delete(long id, long idAutore);	
	
	/* @return Tutti gli articoli nel database */
	public List<ArticoloDTO> getAll();
	
	/* @param 	id Identificativo dell'articolo. 
	 * @return 	Un articolo tramite id */
	public ArticoloDTO getById(long id);
	
	/* @return 	Gli articoli che hanno un determinato stato*/
	public List<ArticoloDTO> getbyStato(ArticoloDTO.Stato stato);
	
	/* @param	id_utente	Identificativo dell'utente
	 * @return  			Tutti gli articoli pubblici più quelli in bozza di un utente specifico*/
	public List<ArticoloDTO> getPubbliciAndBozze(long id_utente);
	
}
