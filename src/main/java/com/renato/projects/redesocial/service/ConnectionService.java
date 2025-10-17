package com.renato.projects.redesocial.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.renato.projects.redesocial.controller.dto.connection.PostConnectionDTO;
import com.renato.projects.redesocial.domain.Connection;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.repository.ConnectionRepository;

@Service
public class ConnectionService {

	ConnectionRepository connectionRepository;
	UserProfileService userProfileService;
	UserAccountService userAccountService;
	
	public ConnectionService(
			ConnectionRepository connectionRepository, 
			UserProfileService userProfileService,
			UserAccountService userAccountService) {
		super();
		this.connectionRepository = connectionRepository;
		this.userProfileService = userProfileService;
		this.userAccountService = userAccountService;
	}
	
	public void postConnection(PostConnectionDTO postConnectionDTO) {
		UserProfile userProfileTarget = userProfileService.findByid(postConnectionDTO.idTarget());
		UserProfile userCurrent = userAccountService.getCurrentUserProfile();
		Connection connection = new Connection(userCurrent, userProfileTarget);
		connectionRepository.save(connection);		
	}

	public Connection getConnection(Long id) {
		UserProfile userProfileTarget = userProfileService.findByid(id);
		UserProfile userCurrent = userAccountService.getCurrentUserProfile();
		
		Optional<Connection> connectionOptional = connectionRepository.findByInitiatorIdAndFriendId(userProfileTarget.getId(), userCurrent.getId());
		Connection connection = null;
		if(connectionOptional.isEmpty()) {
			connection = connectionRepository.findByInitiatorIdAndFriendId(userCurrent.getId(), userProfileTarget.getId()).orElseThrow(
					()-> new NoSuchElementException("Connection Not Found"));
		}else {
			connection = connectionOptional.get();
		}
		return connection;
	}
}
