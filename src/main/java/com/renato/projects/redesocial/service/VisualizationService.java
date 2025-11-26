package com.renato.projects.redesocial.service;

import org.springframework.stereotype.Service;

import com.renato.projects.redesocial.domain.Post;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.domain.Visualization;
import com.renato.projects.redesocial.repository.VisualizationRepository;

@Service
public class VisualizationService {

	public VisualizationRepository visualizationRepository;
	public PostService postService;
	public UserAccountService userAccountService;

	public VisualizationService(VisualizationRepository visualizationRepository, PostService postService, 
			UserAccountService userAccountService) {
		super();
		this.visualizationRepository = visualizationRepository;
		this.postService = postService;
		this.userAccountService = userAccountService;
	}

	public void postVisualization(Long postId) {
		UserProfile userProfile = userAccountService.getCurrentUserProfile();
		Post post = postService.findPostById(postId);
		Visualization visualization = new Visualization(userProfile, post);
		
		
	}
	
	
}
