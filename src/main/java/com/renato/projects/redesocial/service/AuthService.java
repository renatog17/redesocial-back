package com.renato.projects.redesocial.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.renato.projects.redesocial.controller.dto.auth.AuthDTO;
import com.renato.projects.redesocial.controller.dto.auth.LoginResponseDTO;
import com.renato.projects.redesocial.domain.UserAccount;
import com.renato.projects.redesocial.repository.UserAccountRepository;
import com.renato.projects.redesocial.security.service.TokenService;

@Service
public class AuthService {

	private AuthenticationManager authenticationManager;
	private TokenService tokenService;

	public AuthService(AuthenticationManager authenticationManager, UserAccountRepository userRepository,
			TokenService tokenService) {
		super();
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	public LoginResponseDTO login(AuthDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.userName(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((UserAccount) auth.getPrincipal());
		return new LoginResponseDTO(token);
	}
}
