package com.renato.projects.redesocial.controller.dto.post;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.renato.projects.redesocial.controller.dto.ReadImageDTO;
import com.renato.projects.redesocial.domain.Post;

public record ReadPostDTO(Long id, LocalDate date, String content, Long likes, List<ReadImageDTO> images) {

	 public ReadPostDTO(Post post) {
	        this(
	            post.getId(),
	            post.getDate(),
	            post.getContent(),
	            post.getLikes(),
	            post.getImages()
	                .stream()
	                .map(ReadImageDTO::new)
	                .collect(Collectors.toList())
	        );
	    }
}
