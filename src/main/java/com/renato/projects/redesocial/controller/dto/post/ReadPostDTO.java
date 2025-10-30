package com.renato.projects.redesocial.controller.dto.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.renato.projects.redesocial.controller.dto.ReadImageDTO;
import com.renato.projects.redesocial.domain.Post;

public record ReadPostDTO(Long id, 
		LocalDateTime createdAt, 
		String content, 
		Long likes, 
		List<ReadImageDTO> images,
		String userProfileName,
		String userProfileNickName, 
		String userProfilePhotoURL) {

	 public ReadPostDTO(Post post) {
	        this(
	            post.getId(),
	            post.getCreatedAt(),
	            post.getContent(),
	            post.getLikes(),
	            post.getImages()
	                .stream()
	                .map(ReadImageDTO::new)
	                .collect(Collectors.toList()),
	            post.getUserProfile().getName(),
	            post.getUserProfile().getNickname(),
	            post.getUserProfile().getPhotoUrl()
	        );
	    }
}
