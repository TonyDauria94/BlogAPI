package it.rdev.blog.api.dao;

import org.springframework.data.repository.CrudRepository;

import it.rdev.blog.api.dao.entity.Tag;

public interface TagDao extends CrudRepository<Tag, String> {

}
