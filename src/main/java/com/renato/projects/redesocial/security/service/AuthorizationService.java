package com.renato.projects.redesocial.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.renato.projects.redesocial.repository.UserAccountRepository;

@Service
public class AuthorizationService implements UserDetailsService{

	@Autowired
	UserAccountRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUserName(username).get();
	}

}