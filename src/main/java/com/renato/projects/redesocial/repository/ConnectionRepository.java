package com.renato.projects.redesocial.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.redesocial.domain.Connection;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.domain.enums.ConnectionStatus;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

	Optional<Connection> findByInitiatorIdAndFriendId(Long initiatorId, Long friendId);

	List<Connection> findByFriendAndStatus(UserProfile friend, ConnectionStatus status);
	
	List<Connection> findByInitiatorId(Long id);

	List<Connection> findByFriendId(Long id);
}