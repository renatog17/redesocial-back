package com.renato.projects.redesocial.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.renato.projects.redesocial.controller.dto.connection.AcceptConnectionDTO;
import com.renato.projects.redesocial.controller.dto.connection.PostConnectionDTO;
import com.renato.projects.redesocial.domain.Connection;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.domain.enums.ConnectionStatus;
import com.renato.projects.redesocial.repository.ConnectionRepository;

import jakarta.transaction.Transactional;

@Service
public class ConnectionService {

	private ConnectionRepository connectionRepository;
	private UserProfileService userProfileService;
	private UserAccountService userAccountService;
	private NotificationService notificationService;

	public ConnectionService(ConnectionRepository connectionRepository, UserProfileService userProfileService,
			UserAccountService userAccountService, NotificationService notificationService) {
		super();
		this.connectionRepository = connectionRepository;
		this.userProfileService = userProfileService;
		this.userAccountService = userAccountService;
		this.notificationService = notificationService;
	}

	public void postConnection(PostConnectionDTO postConnectionDTO) {
		UserProfile userProfileTarget = userProfileService.findByid(postConnectionDTO.idTarget());
		UserProfile userCurrent = userAccountService.getCurrentUserProfile();

		Connection connection = new Connection(userCurrent, userProfileTarget);
		connectionRepository.save(connection);

		FriendRequestDTO request = new FriendRequestDTO(userCurrent.getUserAccount().getUsername());
		notificationService.sendFriendRequest(userProfileTarget.getUserAccount().getUsername(), request);
	}

	public Connection getConnection(Long id) {
		UserProfile userProfileTarget = userProfileService.findByid(id);
		UserProfile userCurrent = userAccountService.getCurrentUserProfile();

		Optional<Connection> connectionOptional = connectionRepository
				.findByInitiatorIdAndFriendId(userProfileTarget.getId(), userCurrent.getId());
		Connection connection = null;
		if (connectionOptional.isEmpty()) {
			connection = connectionRepository
					.findByInitiatorIdAndFriendId(userCurrent.getId(), userProfileTarget.getId())
					.orElseThrow(() -> new NoSuchElementException("Connection Not Found"));
		} else {
			connection = connectionOptional.get();
		}
		return connection;
	}

	public List<Connection> getPendingConnections() {
		UserProfile userCurrent = userAccountService.getCurrentUserProfile();
		return connectionRepository.findByFriendAndStatus(userCurrent, ConnectionStatus.PENDING);
	}

	@Transactional
	public void acceptConnection(AcceptConnectionDTO acceptConnectionDTO) {	
		Connection connection = 
				connectionRepository
				.findById(acceptConnectionDTO.id()).orElseThrow(() -> new NoSuchElementException("Conexao nao encontrada"));
		
		connection.setStatus(ConnectionStatus.ACCEPTED);
	}
}
