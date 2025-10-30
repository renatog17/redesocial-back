package com.renato.projects.redesocial.controller.dto.userprofile;

import java.util.List;
import java.util.stream.Collectors;

import com.renato.projects.redesocial.controller.dto.post.ReadPostDTO;
import com.renato.projects.redesocial.domain.UserProfile;

public record ReadPrivateUserProfileDTO(
		//aqui pode ser retornado o nickname tbm
		String bio,
		List<ReadPostDTO> posts
		) {

	public ReadPrivateUserProfileDTO(UserProfile userProfile){
		
		this(userProfile.getBio(), 
			 userProfile.getPosts()
					.stream()
					.map(post -> new ReadPostDTO(post))
					.collect(Collectors.toList()));
	}
}
