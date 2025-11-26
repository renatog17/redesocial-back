package com.renato.projects.redesocial.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.redesocial.controller.dto.auth.AuthDTO;
import com.renato.projects.redesocial.controller.dto.userprofile.ReadUserProfileDTO;
import com.renato.projects.redesocial.domain.UserAccount;
import com.renato.projects.redesocial.security.service.TokenService;
import com.renato.projects.redesocial.service.AuthService;
import com.renato.projects.redesocial.service.UserAccountService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/auth/login")
@RestController
public class AuthController {

	private TokenService tokenService;
	private AuthService authService;
	private UserAccountService userAccountService;
	
	public AuthController(TokenService tokenService, AuthService authService,UserAccountService userAccountService) {
		super();
		this.tokenService = tokenService;
		this.authService = authService;
		this.userAccountService = userAccountService;
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> login(@RequestBody AuthDTO authDTO, HttpServletResponse response) {
		String token = authService.login(authDTO).token();
		ReadUserProfileDTO readUserProfileDTO = new ReadUserProfileDTO(userAccountService.findByUserName(authDTO.userName()).getProfile());
		ResponseCookie cookie = ResponseCookie.from("token", token)
				.httpOnly(true)
				.secure(true) // true se estiver em HTTPS; FORTE CANDIDATO A IR PARA PROFILE
				.sameSite("Lax") // ou "Strict" para mais proteção
				.path("/").maxAge(Duration.ofHours(2)).build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(readUserProfileDTO);
	}

	@GetMapping("/check")
	public ResponseEntity<?> check(@CookieValue(name = "token", required = false) String token) {
		if (token != null && !token.isEmpty()) {
			String subject = tokenService.validateToken(token);
			if (!subject.isEmpty()) {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				UserAccount principal = (UserAccount) auth.getPrincipal();
				ReadUserProfileDTO readUserProfileDTO = new ReadUserProfileDTO(principal.getProfile());
				return ResponseEntity.ok(readUserProfileDTO);
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from("token", "").httpOnly(true).secure(true).path("/").maxAge(0)
				.build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Logout realizado");
	}
}
