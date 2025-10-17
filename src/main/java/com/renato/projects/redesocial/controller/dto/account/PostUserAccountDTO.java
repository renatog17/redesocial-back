package com.renato.projects.redesocial.controller.dto.account;

import com.renato.projects.redesocial.controller.dto.userprofile.PostUserProfileDTO;
import com.renato.projects.redesocial.domain.UserAccount;

import jakarta.validation.constraints.NotBlank;

public record PostUserAccountDTO(
		@NotBlank
		String userName,
		@NotBlank
		String password,
		PostUserProfileDTO profile) {
	
		public UserAccount toModel() {
			UserAccount userAccount = new UserAccount(userName, password);
			userAccount.setProfile(profile.toModel(userAccount));
			return userAccount;
		}

}
