package it.rdev.blog.api.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(User.class)
public class User_ {
	
	public static volatile SingularAttribute<User, Long> id;
	  public static volatile SingularAttribute<User, String> username;
	  public static volatile SingularAttribute<User, String> password;
	  public static volatile ListAttribute<User, Articolo> articoli;
	  
	
}
