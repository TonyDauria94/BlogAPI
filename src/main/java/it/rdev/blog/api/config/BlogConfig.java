package it.rdev.blog.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import it.rdev.blog.api.service.BlogArticoloDetailsService;
import it.rdev.blog.api.service.BlogCategoriaDetailsService;
import it.rdev.blog.api.service.BlogTagDetailsService;
import it.rdev.blog.api.service.impl.BlogArticoloDetailsServiceImpl;
import it.rdev.blog.api.service.impl.BlogCategoriaDetailsServiceImpl;
import it.rdev.blog.api.service.impl.BlogTagDetailsServiceImpl;

@Configuration
@EnableTransactionManagement
public class BlogConfig {

	@Bean
	@Primary
	public BlogCategoriaDetailsService blogCategoriaDetailsService() {
		return new BlogCategoriaDetailsServiceImpl();
	}
	
	@Bean
	@Primary
	public BlogTagDetailsService blogTagDetailsService() {
		return new BlogTagDetailsServiceImpl();
	}
	
	@Bean
	@Primary
	public BlogArticoloDetailsService blogArticoloDetailsService() {
		return new BlogArticoloDetailsServiceImpl();
	}
	
}
