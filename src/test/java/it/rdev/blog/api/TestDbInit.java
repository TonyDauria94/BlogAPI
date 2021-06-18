package it.rdev.blog.api;

import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import it.rdev.blog.api.dao.UserDAO;

@Sql({"/database_init.sql"})
public class TestDbInit {
	
	@AfterAll
	public static void destroy(@Autowired UserDAO userDao) {
		userDao.deleteAll();
	}

}
