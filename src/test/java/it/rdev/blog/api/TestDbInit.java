package it.rdev.blog.api;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import it.rdev.blog.api.dao.ArticoloDAO;
import it.rdev.blog.api.dao.CategoriaDAO;
import it.rdev.blog.api.dao.TagDAO;
import it.rdev.blog.api.dao.UserDAO;

@Sql(scripts = {"/database_init.sql"})
public class TestDbInit {
	
	// NON STATIC CON FOREACH ED AFTEREACH
	// AFTERALL PUO' ESSERE STATICO
	@AfterEach
	public void destroy(@Autowired UserDAO userDao,
			@Autowired ArticoloDAO articoloDao,
			@Autowired CategoriaDAO categoriaDao,
			@Autowired TagDAO tagDao) {
		
		userDao.deleteAll();
		articoloDao.deleteAll();
		categoriaDao.deleteAll();
		tagDao.deleteAll();
	}

}
