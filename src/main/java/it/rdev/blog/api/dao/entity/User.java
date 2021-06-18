package it.rdev.blog.api.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String username;
	
	@Column(nullable = false)
	@JsonIgnore
	private String password;
	
	@OneToMany(mappedBy = "autore", fetch = FetchType.LAZY, 
			orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Articolo> articoli;

	public long getId() {
		return id;
	}

	public User setId(long id) {
		this.id = id;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username != null && !username.equals(""))
			this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public List<Articolo> getArticoli() {
		return articoli;
	}

	public void setArticoli(List<Articolo> articoli) {
		this.articoli = articoli;
	}
	
	
}