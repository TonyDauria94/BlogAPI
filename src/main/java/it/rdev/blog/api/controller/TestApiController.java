package it.rdev.blog.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import it.rdev.blog.api.config.JwtTokenUtil;
import it.rdev.blog.api.controller.dto.ArticoloDTO;

import it.rdev.blog.api.service.ArticoloDetailsService;

@RestController
public class TestApiController {
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private ArticoloDetailsService as;
	
	@GetMapping({ "/api/test" })
	public String get() {
	
		return "Risorsa Accesibile";
	}
	
	
	@GetMapping({ "/api" })
	public List<ArticoloDTO> getTest() {
	
		Map<String, String> m = new HashMap<>();
		
		m.put("testo", "2");
		
		return as.getByFilters(m);
		
	}
	
	@PostMapping({ "/api/test" })
	public String post(@RequestHeader(name = "Authorization") String token) {
		String username = null;
		Long id = null;
		if(token != null && token.startsWith("Bearer")) {
			token = token.replaceAll("Bearer ", "");
			username = jwtUtil.getUsernameFromToken(token);
			id = jwtUtil.getUserIdFromToken(token);
			
		}
		return "Risorsa Protetta ["+ id + "] [" + username + "]";
	}
	
}