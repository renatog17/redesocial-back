package com.renato.projects.redesocial.controller.dto.userprofile;

import com.renato.projects.redesocial.domain.UserProfile;

public record ReadUserProfileDTO(
		Long id,
		String name,
		String nickName,
		String photoUrl,
		ReadPrivateUserProfileDTO readPrivateUserProfileDTO
		) {

	public ReadUserProfileDTO(UserProfile userProfile, ReadPrivateUserProfileDTO readPrivateUserProfileDTO){
		this(userProfile.getId(), userProfile.getName(), userProfile.getNickname(), 
				userProfile.getPhotoUrl(),
				readPrivateUserProfileDTO
				); 
	}
}
