package it.rdev.blog.api.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ArticoloDTO {

	private long id;
	private String titolo;
	private String sottotitolo;
	private String testo;
	private LocalDateTime dataPublicazione;
	private LocalDateTime dataModifica;
	private LocalDateTime dataCreazione;
	private String stato;
	
	private UserDTO autore;
	private CategoriaDTO categoria;
	private List<TagDTO> tags;
	
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

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public UserDTO getAutore() {
		return autore;
	}

	public void setAutore(UserDTO autore) {
		this.autore = autore;
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public List<TagDTO> getTags() {
		return tags;
	}

	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}
	
}
