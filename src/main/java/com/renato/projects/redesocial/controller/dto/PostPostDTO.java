package com.renato.projects.redesocial.controller.dto;

import java.util.List;

import com.renato.projects.redesocial.domain.Post;

public record PostPostDTO(
		String content,
		List<String> imgs
		) {
	
	public Post toModel() {
		return new Post(content, imgs);
	}

}
