package com.renato.projects.redesocial.controller.dto.userprofile;

import java.time.LocalDate;

import com.renato.projects.redesocial.domain.UserAccount;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.domain.enums.Gender;

public record PostUserProfileDTO(
		String name,
		String nickname,
		LocalDate birthDate,
		Gender gender) {
	
	public UserProfile toModel(UserAccount userAccount) {
		return new UserProfile(name, nickname, birthDate, gender, userAccount);
	}

}
