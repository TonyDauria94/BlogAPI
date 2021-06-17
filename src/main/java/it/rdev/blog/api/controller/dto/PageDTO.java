package it.rdev.blog.api.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "tipoContenuto", "totaleRisultati", "paginaCorrente", "ultimaPagina", "numeroElementiPagina", "contenuto" })
public class PageDTO<T> {

	private String tipoContenuto;
	private Long totaleRisultati;
	private Integer paginaCorrente;
	private Integer ultimaPagina;
	private List<T> contenuto;
	

	public String getTipoContenuto() {
		return tipoContenuto;
	}

	public List<T> getContenuto() {
		return contenuto;
	}

	public void setContenuto(List<T> contenuto) {
		// Se la lista non è null e non è vuota
		if (contenuto != null && !contenuto.isEmpty()) {
			// imposto il tipo di contenuto della pagina recuperando il nome della classe
			// ed eliminando l'eventuale stringa DTO.
			tipoContenuto = contenuto.get(0).getClass().getSimpleName().replace("DTO", "");
			this.contenuto = contenuto;
		}
		
		
	}

	public Integer getPaginaCorrente() {
		return paginaCorrente;
	}

	public void setPaginaCorrente(Integer paginaCorrente) {
		this.paginaCorrente = paginaCorrente;
	}

	public Integer getUltimaPagina() {
		return ultimaPagina;
	}

	public void setUltimaPagina(Integer ultimaPagina) {
		this.ultimaPagina = ultimaPagina;
	}

	public Long getTotaleRisultati() {
		return totaleRisultati;
	}

	public void setTotaleRisultati(Long totaleRisultati) {
		this.totaleRisultati = totaleRisultati;
	}
	
	public int getNumeroElementiPagina() {
		if (contenuto != null) {
			return contenuto.size();
		} else
			return 0;
	}
	
}
