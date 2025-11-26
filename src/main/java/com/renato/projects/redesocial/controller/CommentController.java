package com.renato.projects.redesocial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.redesocial.controller.dto.comment.PostCommentDTO;
import com.renato.projects.redesocial.service.CommentService;

@RestController
@RequestMapping("post/comment")
public class CommentController {

	public CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	@PostMapping("{postId}")
	public ResponseEntity<?> postComment(@RequestBody PostCommentDTO postCommentDTO,
			@PathVariable Long postId){
		System.out.println(postCommentDTO.content());
		commentService.save(postCommentDTO, postId);
		return ResponseEntity.ok().build();
	}
}
