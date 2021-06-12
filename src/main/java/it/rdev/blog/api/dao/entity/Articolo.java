package it.rdev.blog.api.dao.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "articoli")
public class Articolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "titolo", nullable = false)
	private String titolo;
	
	@Column(name = "sottotitolo")
	private String sottotitolo;
	
	@Column(name = "testo", nullable = false)
	private String testo;
	
	@Column(name = "data_publicazione")
	private LocalDateTime dataPublicazione;
	
	@Column(name = "data_modifica")
	private LocalDateTime dataModifica;
	
	@Column(name = "data_creazione", nullable = false)
	private LocalDateTime dataCreazione;
	
	@Column(name = "stato", nullable = false)
	private String stato;
	
	@ManyToOne()
	@JoinColumn(name = "autore", referencedColumnName="id", nullable = false)
	private User autore;
	
	// Dato che il nome della categoria è anche la chiave primaria
	// impostare una relazione tra le tabelle ci costerebbe una query inutile
	// in più.
	
	// @ManyToOne()
	// @JoinColumn(name = "categoria", referencedColumnName="categoria")
	@Column(name = "categoria", nullable = false)
	private String categoria;

	@ManyToMany
	@JoinTable(
	  name = "articolo_tag", 
	  joinColumns = @JoinColumn(name = "id_articolo"), 
	  inverseJoinColumns = @JoinColumn(name = "tag"))
	private List<Tag> tags;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public User getAutore() {
		return autore;
	}

	public void setAutore(User autore) {
		this.autore = autore;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
}
