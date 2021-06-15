package it.rdev.blog.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import it.rdev.blog.api.service.ArticoloDetailsService;
import it.rdev.blog.api.service.CategoriaDetailsService;
import it.rdev.blog.api.service.TagDetailsService;
import it.rdev.blog.api.service.impl.ArticoloDetailsServiceImpl;
import it.rdev.blog.api.service.impl.CategoriaDetailsServiceImpl;
import it.rdev.blog.api.service.impl.TagDetailsServiceImpl;

@Configuration
@EnableTransactionManagement
public class BlogConfig {

	@Bean
	@Primary
	public CategoriaDetailsService blogCategoriaDetailsService() {
		return new CategoriaDetailsServiceImpl();
	}
	
	@Bean
	@Primary
	public TagDetailsService blogTagDetailsService() {
		return new TagDetailsServiceImpl();
	}
	
	@Bean
	@Primary
	public ArticoloDetailsService blogArticoloDetailsService() {
		return new ArticoloDetailsServiceImpl();
	}
	
}
