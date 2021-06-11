package it.rdev.blog.api.dao.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categorie")
public class Categoria {

	@Id
	@Column(name = "nome")
	private String nome;
	
	@OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY, 
			orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Articolo> articoli;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
