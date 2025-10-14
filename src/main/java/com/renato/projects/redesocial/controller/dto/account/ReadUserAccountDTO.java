package com.renato.projects.redesocial.controller.dto.account;

import com.renato.projects.redesocial.domain.UserAccount;

public record ReadUserAccountDTO(
		Long id,
		String userName
		) {

	public ReadUserAccountDTO(UserAccount userAccount) {
		this(userAccount.getId(), userAccount.getUsername());
	}
}
