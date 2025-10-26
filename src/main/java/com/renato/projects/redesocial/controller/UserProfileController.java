package com.renato.projects.redesocial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.redesocial.service.UserProfileService;

@RequestMapping("/user/profile")
@RestController
public class UserProfileController {

	private UserProfileService userProfileService;

	public UserProfileController(UserProfileService userProfileService) {
		super();
		this.userProfileService = userProfileService;
	}

	@GetMapping("/{nickname}")
	public ResponseEntity<?> getUserProfileByNickname(@PathVariable String nickname) {
		return ResponseEntity.ok(userProfileService.findByNickname(nickname));
	}
}
