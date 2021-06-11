package it.rdev.blog.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.rdev.blog.api.controller.dto.TagDTO;
import it.rdev.blog.api.dao.TagDao;
import it.rdev.blog.api.dao.entity.Tag;
import it.rdev.blog.api.service.BlogTagDetailsService;


@Service
public class BlogTagDetailsServiceImpl implements BlogTagDetailsService {
	
	@Autowired
	private TagDao tagDao;
	
	@Override
	public List<TagDTO> findAll() {
		
		Iterable<Tag> it = tagDao.findAll();
		List<TagDTO> list = new ArrayList<>();
		for (Tag t : it) {
			list.add(toDto(t));
		}
		
		return list;
		
	}
	
	private TagDTO toDto(Tag t) {
		TagDTO dto = new TagDTO();
		dto.setTag(t.getTag());
		return dto;
	}
	
}
