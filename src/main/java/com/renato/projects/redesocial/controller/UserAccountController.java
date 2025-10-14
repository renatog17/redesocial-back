package com.renato.projects.redesocial.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.renato.projects.redesocial.controller.dto.account.PostUserAccountDTO;
import com.renato.projects.redesocial.controller.dto.account.ReadUserAccountDTO;
import com.renato.projects.redesocial.domain.UserAccount;
import com.renato.projects.redesocial.service.UserAccountService;

import jakarta.validation.Valid;

@RequestMapping("/user/account")
@RestController
public class UserAccountController {

	private UserAccountService userAccountService;

	public UserAccountController(UserAccountService userAccountService) {
		super();
		this.userAccountService = userAccountService;
	}

	@PostMapping
	public ResponseEntity<?> postUserAccount(@RequestBody @Valid PostUserAccountDTO userAccountDTO, UriComponentsBuilder uriComponentsBuilder) {
		UserAccount userAccount = userAccountService.save(userAccountDTO);
		URI uri = uriComponentsBuilder.path("/user/account/{id}").buildAndExpand(userAccount.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReadUserAccountDTO(userAccount));
	}
}
