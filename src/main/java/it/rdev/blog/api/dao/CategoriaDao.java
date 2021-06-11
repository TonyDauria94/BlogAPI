package it.rdev.blog.api.dao;

import org.springframework.data.repository.CrudRepository;

import it.rdev.blog.api.dao.entity.Categoria;

public interface CategoriaDao extends CrudRepository<Categoria, String> {

}
