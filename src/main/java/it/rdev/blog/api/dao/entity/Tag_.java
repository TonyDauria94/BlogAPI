package it.rdev.blog.api.dao.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Tag.class)
public class Tag_ {

	public static volatile SingularAttribute<Tag, String> tag;
	
	public static volatile ListAttribute<Tag, Articolo> articoli;
	
}
