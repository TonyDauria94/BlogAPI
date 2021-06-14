package it.rdev.blog.api.dao.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Articolo.class)
public class Articolo_ {


	  public static volatile SingularAttribute<Articolo, Long> id;
	  public static volatile SingularAttribute<Articolo, String> titolo;
	  public static volatile SingularAttribute<Articolo, String> sottotitolo;
	  public static volatile SingularAttribute<Articolo, String> testo;
	  public static volatile SingularAttribute<Articolo, LocalDateTime> dataPubblicazione;
	  public static volatile SingularAttribute<Articolo, LocalDateTime> dataModifica;
	  public static volatile SingularAttribute<Articolo, LocalDateTime> dataCreazione;
	  public static volatile SingularAttribute<Articolo, String> stato;
	  public static volatile SingularAttribute<Articolo, User> autore;
	  public static volatile SingularAttribute<Articolo, String> categoria;
	  public static volatile ListAttribute<Articolo, List<Tag>> tags;
	
}
