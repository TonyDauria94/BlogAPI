package it.rdev.blog.api.controller.dto;

import java.util.List;

public class PageDTO<T> {

	private String tipoContenuto;
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
			// ed eliminando la stringa DTO.
			tipoContenuto = contenuto.get(0).getClass().getSimpleName().replace("DTO", "");
			this.contenuto = contenuto;
		}
	}
	
	
}
