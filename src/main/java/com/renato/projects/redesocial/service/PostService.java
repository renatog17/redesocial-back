package com.renato.projects.redesocial.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.renato.projects.redesocial.controller.dto.post.PostPostDTO;
import com.renato.projects.redesocial.controller.dto.post.ReadPostDTO;
import com.renato.projects.redesocial.domain.Connection;
import com.renato.projects.redesocial.domain.Post;
import com.renato.projects.redesocial.domain.UserAccount;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.repository.ConnectionRepository;
import com.renato.projects.redesocial.repository.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	private ConnectionRepository connectionRepository;

	public PostService(PostRepository postRepository, ConnectionRepository connectionRepository) {
		super();
		this.postRepository = postRepository;
		this.connectionRepository = connectionRepository;
	}

	
	public Post save(PostPostDTO postPostDTO) {
		//preciso achar as hash tags
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAccount user = (UserAccount) authentication.getPrincipal();
		Post post = postPostDTO.toModel();
		
		post.setUserProfile(user.getProfile());
		
		return postRepository.save(post);
	}


	public List<ReadPostDTO> getPosts() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAccount user = (UserAccount) authentication.getPrincipal();
		
		UserProfile profile = user.getProfile();
		
		List<Post> posts = new ArrayList<Post>();
		
		List<Connection> findByIniatorId = connectionRepository.findByInitiatorId(profile.getId());
		
		for (Connection connection : findByIniatorId) {
			posts.addAll(connection.getFriend().getPosts());
		}
		
		List<Connection> findByFriendId = connectionRepository.findByFriendId(profile.getId());
		
		for (Connection connection : findByFriendId) {
			posts.addAll(connection.getInitiator().getPosts());
		}
		
		return posts.stream().map(ReadPostDTO::new).collect(Collectors.toList());
	}

}
