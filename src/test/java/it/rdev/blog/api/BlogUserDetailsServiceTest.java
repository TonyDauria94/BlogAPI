package it.rdev.blog.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import it.rdev.blog.api.dao.UserDAO;
import it.rdev.blog.api.dao.entity.User;
import it.rdev.blog.api.service.BlogUserDetailsService;

@ExtendWith(MockitoExtension.class)
class BlogUserDetailsServiceTest {
	
	BlogUserDetailsService userService;
	
	@Mock UserDAO userDao;
	
	@BeforeEach
	public void init() {
		User user = new User();
		user.setId(100);
		user.setUsername("ddinuzzo");
		user.setPassword("password_100");
		
		Mockito.lenient().when(userDao.findByUsername("ddinuzzo")).thenReturn(user);
		
		userService = new BlogUserDetailsService(userDao);
//		UserDetails ud = new BlogUserDetails(1L, "ddinuzzo", "password01", null);
//		Mockito.lenient()
//			.when(userService.loadUserByUsername(Mockito.anyString())).thenReturn(ud);
	}
	
	@Test
	void findUserByUsernameTest() {
		UserDetails ud = userService.loadUserByUsername("ddinuzzo");
		assertAll(
				() -> assertNotNull(ud, "user details shouldn't be null"),
				() -> assertEquals(ud.getUsername(), "ddinuzzo", "the username field should be equals to ddinuzzo")
				);
	}

}
