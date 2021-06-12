package it.rdev.blog.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.rdev.blog.api.dao.entity.User;

@Repository
public interface UserDAO extends CrudRepository<User, Integer> {
	
	User findByUsername(String username);
	
}