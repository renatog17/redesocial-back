package com.renato.projects.redesocial.service;

import java.util.NoSuchElementException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.renato.projects.redesocial.controller.dto.auth.AuthDTO;
import com.renato.projects.redesocial.controller.dto.auth.LoginResponseDTO;
import com.renato.projects.redesocial.controller.dto.userprofile.ReadUserProfileDTO;
import com.renato.projects.redesocial.domain.UserAccount;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.repository.UserAccountRepository;
import com.renato.projects.redesocial.security.service.TokenService;

@Service
public class AuthService {

	private AuthenticationManager authenticationManager;
	private TokenService tokenService;
	private UserAccountRepository userAccountRepository;

	public AuthService(AuthenticationManager authenticationManager, UserAccountRepository userRepository,
			TokenService tokenService, UserAccountRepository userAccountRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
		this.userAccountRepository = userAccountRepository;
	}

	public LoginResponseDTO login(AuthDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.userName(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((UserAccount) auth.getPrincipal());
		return new LoginResponseDTO(token);
	}
	
	public ReadUserProfileDTO getUserAuthenticated(String userName) {
		UserAccount userAccount = (UserAccount) userAccountRepository.findByUserName(userName).orElseThrow(() -> new NoSuchElementException());
		UserProfile userProfile = userAccount.getProfile();
		return new ReadUserProfileDTO(userProfile);
	}
}
