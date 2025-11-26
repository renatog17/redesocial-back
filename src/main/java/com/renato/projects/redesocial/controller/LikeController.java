package com.renato.projects.redesocial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.redesocial.service.LikeService;

@RestController
@RequestMapping("/like")
public class LikeController {

	public LikeService likeService;

	public LikeController(LikeService likeService) {
		super();
		this.likeService = likeService;
	}

	@PostMapping("/post/{postId}")
	public ResponseEntity<?> postLike(@PathVariable Long postId){
		likeService.postLike(postId);
		return ResponseEntity.ok().build();
	}
}
