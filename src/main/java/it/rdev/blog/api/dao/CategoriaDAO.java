package it.rdev.blog.api.dao;

import org.springframework.data.repository.CrudRepository;

import it.rdev.blog.api.dao.entity.Categoria;

public interface CategoriaDAO extends CrudRepository<Categoria, String> {

}
