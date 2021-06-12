package it.rdev.blog.api.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ArticoloDTO {
	
	/** Enumeratore per lo stato dell'articolo.
	 *  Usando l'enumeratore posso evitare che nel db vengano aggiunti articoli
	 *  in uno stato non gestito dal controller.
	 * 
	 *  PUBBLICATO : Articolo pubblicato, chiunque può leggerlo.
	 *  BOZZA      : Articolo in bozza, solo l'autore può leggerlo, modificarlo o pubblicarlo.
	 *  
	 */
	public enum Stato {

		PUBBLICATO("pubblicato"),
		BOZZA("bozza");
		
		private String valore;
		
		Stato(String valore) {
			this.valore = valore;
		}
		
		public String getValore() {
			return valore;
		}
		
	}

	private long id;
	private String titolo;
	private String sottotitolo;
	private String testo;
	private LocalDateTime dataPublicazione;
	private LocalDateTime dataModifica;
	private LocalDateTime dataCreazione;
	private Stato stato;
	
	private String autore;
	private String categoria;
	private List<String> tags;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getSottotitolo() {
		return sottotitolo;
	}

	public void setSottotitolo(String sottotitolo) {
		this.sottotitolo = sottotitolo;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public LocalDateTime getDataPublicazione() {
		return dataPublicazione;
	}

	public void setDataPublicazione(LocalDateTime dataPublicazione) {
		this.dataPublicazione = dataPublicazione;
	}

	public LocalDateTime getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(LocalDateTime dataModifica) {
		this.dataModifica = dataModifica;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	/* Per lo stato restituisco il valore e non l'enum. */
	public String getStato() {
		return stato.getValore();
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
}
