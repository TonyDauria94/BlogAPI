package it.rdev.blog.api.dao;

import org.springframework.data.repository.CrudRepository;

import it.rdev.blog.api.dao.entity.Tag;

public interface CategoriaDao extends CrudRepository<Tag, String> {

}
