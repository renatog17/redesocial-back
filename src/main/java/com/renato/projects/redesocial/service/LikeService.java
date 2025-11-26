package com.renato.projects.redesocial.service;

import org.springframework.stereotype.Service;

import com.renato.projects.redesocial.domain.Post;
import com.renato.projects.redesocial.domain.PostLike;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.repository.PostLikeRepository;

@Service
public class LikeService {

	public PostLikeRepository likeRepository;
	public PostService postService;
	public UserAccountService userAccountService;

	public LikeService(PostLikeRepository likeRepository, PostService postService, UserAccountService userAccountService) {
		super();
		this.likeRepository = likeRepository;
		this.postService = postService;
		this.userAccountService = userAccountService;
	}

	public void postLike(Long postId) {
		Post post = postService.findPostById(postId);
		UserProfile userProfile = userAccountService.getCurrentUserProfile();
		PostLike like = new PostLike(post, userProfile);
		likeRepository.save(like);
	}
	
	
}
