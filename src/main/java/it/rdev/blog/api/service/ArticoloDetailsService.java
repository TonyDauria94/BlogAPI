package it.rdev.blog.api.service;

import java.util.List;
import java.util.Map;

import it.rdev.blog.api.controller.dto.ArticoloDTO;

public interface ArticoloDetailsService {

	/* Aggiunge/modifica un articolo al database */
	public void saveOrUpdate(ArticoloDTO articolo);
	
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
	
	
	/* Effettua una ricerca per gli utenti loggati utilizzando i filtri passati tramite la mappa 
	 * 
	 * @param filters 	mappa di filtri
	 * @param iserId	Identificativo dell'utente che si è loggato
	 * */
	public List<ArticoloDTO> getByFilters(Map<String, String> filters, Long userId);
	
	
	/* Effettua una ricerca pr gli utenti anonimi utilizzando i filtri passati tramite la mappa 
	 * 
	 * @param filters 	mappa di filtri
	 * */
	public List<ArticoloDTO> getByFilters(Map<String, String> filters);
}
