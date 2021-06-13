package it.rdev.blog.api.controller.dto;

public class ExceptionDTO {
	
	private String eccezione;
	private String messaggio;
	
	public String getEccezione() {
		return eccezione;
	}

	public ExceptionDTO setEccezione(String eccezione) {
		this.eccezione = eccezione;
		return this;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public ExceptionDTO setMessaggio(String messaggio) {
		this.messaggio = messaggio;
		return this;
		
		
	}
	
}
