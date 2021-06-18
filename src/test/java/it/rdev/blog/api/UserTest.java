package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import it.rdev.blog.api.dao.UserDAO;
import it.rdev.blog.api.dao.entity.User;
import it.rdev.blog.api.parameters.UserParameterResolver;
import it.rdev.blog.api.service.BlogUserDetailsService;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(UserParameterResolver.class)
class UserTest extends TestDbInit {
	
	@Autowired
	BlogUserDetailsService userService;
	
	@Autowired
	UserDAO userDao;
	
	@BeforeAll
	public static void init(List<User> users) {
		
	}

	@Test
	void findUserByUsername() {
		String username = "ddinuzzo";
		UserDetails ud = userService.loadUserByUsername(username);
		assertAll(
				() -> assertNotNull(ud, "user details shouldn't be null"),
				() -> assertEquals(ud.getUsername(), username, "the username field should be equals to " + username)
				);
	}

	@Test
	@Disabled
	void countUserByUsername() {
		Iterable<User> users = userDao.findAll();
		Integer i = 0;
		for(@SuppressWarnings("unused") User u: users ) {
			i++;
		}
		if( i != 1) {
			fail("users should be only one");
		}
	}

}
