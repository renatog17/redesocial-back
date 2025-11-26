package com.renato.projects.redesocial.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.renato.projects.redesocial.controller.dto.account.PostUserAccountDTO;
import com.renato.projects.redesocial.domain.UserAccount;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.repository.UserAccountRepository;

@Service
public class UserAccountService {

	private UserAccountRepository userAccountRepository;

	public UserAccountService(UserAccountRepository userAccountRepository) {
		super();
		this.userAccountRepository = userAccountRepository;
	}

	public UserAccount save(PostUserAccountDTO postUserAccountDTO) {
		Optional<UserAccount> userAccount = userAccountRepository.findByUserName(postUserAccountDTO.userName());
		if (userAccount.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		return userAccountRepository.save(postUserAccountDTO.toModel());
	}
	
	public UserAccount findByUserName(String userName) {
		return userAccountRepository.findByUserName(userName).orElseThrow(() -> new NoSuchElementException());	
	}
	
	//estou pensando em criar um context service
	//o método abaixo será remanejado para um context service
	
	public UserProfile getCurrentUserProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserAccount userAccount = (UserAccount) authentication.getPrincipal();
		return userAccount.getProfile();
	}
}
