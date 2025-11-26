package com.renato.projects.redesocial.service;

import org.springframework.stereotype.Service;

import com.renato.projects.redesocial.controller.dto.comment.PostCommentDTO;
import com.renato.projects.redesocial.domain.Comment;
import com.renato.projects.redesocial.domain.Post;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.repository.CommentRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {

	public CommentRepository commentRepository;
	public PostService postService;
	public UserAccountService userAccountService;

	public CommentService(CommentRepository commentRepository, PostService postService, 
			UserAccountService userAccountService) {
		super();
		this.commentRepository = commentRepository;
		this.postService = postService;
		this.userAccountService = userAccountService;
	}

	@Transactional
	public void save(PostCommentDTO postCommentDTO, Long postId) {
		Post post = postService.findPostById(postId);
		Comment comment = postCommentDTO.toModel();
		UserProfile userProfile = userAccountService.getCurrentUserProfile();
		
		post.getComments().add(comment);
		comment.setPost(post);
		comment.setUserProfile(userProfile);
		
		commentRepository.save(comment);
	}

	
}
