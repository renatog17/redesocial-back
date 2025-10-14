package com.renato.projects.redesocial.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.renato.projects.redesocial.controller.dto.account.PostUserAccountDTO;
import com.renato.projects.redesocial.domain.UserAccount;
import com.renato.projects.redesocial.repository.UserAccountRepository;

@Service
public class UserAccountService {

	private UserAccountRepository userAccountRepository;

	public UserAccountService(UserAccountRepository userAccountRepository) {
		super();
		this.userAccountRepository = userAccountRepository;
	}

	public UserAccount save(PostUserAccountDTO postUserAccountDTO) {
		Optional<UserDetails> userAccount = userAccountRepository.findByUserName(postUserAccountDTO.userName());
		if (userAccount.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		return userAccountRepository.save(postUserAccountDTO.toModel());
	}

}
