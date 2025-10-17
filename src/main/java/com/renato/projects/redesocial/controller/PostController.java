package com.renato.projects.redesocial.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.renato.projects.redesocial.controller.dto.post.PostPostDTO;
import com.renato.projects.redesocial.service.PostService;

@RequestMapping("/post")
@RestController
public class PostController {

	private PostService postService;
	
	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostPostDTO postPostDTO, UriComponentsBuilder uriComponentsBuilder){
		URI uri = uriComponentsBuilder.path("/post/{id}").buildAndExpand(postService.save(postPostDTO).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
