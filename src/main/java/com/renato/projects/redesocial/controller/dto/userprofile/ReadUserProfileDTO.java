package com.renato.projects.redesocial.controller.dto.userprofile;

import java.util.List;
import java.util.stream.Collectors;

import com.renato.projects.redesocial.controller.dto.post.ReadPostDTO;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.domain.enums.Gender;

public record ReadUserProfileDTO(
		Long id,
		String name,
		String nickName,
		String photoUrl,
		String bio,
		Gender gender,
		List<ReadPostDTO> posts
		) {

	public ReadUserProfileDTO(UserProfile userProfile){
		this(userProfile.getId(), userProfile.getName(), userProfile.getNickname(), 
				userProfile.getPhotoUrl(), userProfile.getBio(), userProfile.getGender(), userProfile.getPosts()
				.stream()
				.map(post -> new ReadPostDTO(post))
				.collect(Collectors.toList())
				); 
	}
}
