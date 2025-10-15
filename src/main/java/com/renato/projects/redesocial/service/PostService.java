package com.renato.projects.redesocial.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.renato.projects.redesocial.controller.dto.PostPostDTO;
import com.renato.projects.redesocial.domain.Post;
import com.renato.projects.redesocial.domain.UserAccount;
import com.renato.projects.redesocial.repository.PostRepository;

public class PostService {

	private PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}

	public Post save(PostPostDTO postPostDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAccount user = (UserAccount) authentication.getPrincipal();
		System.out.println(user.getProfile().getName());
		
		return postRepository.save(postPostDTO.toModel());
	}

}
