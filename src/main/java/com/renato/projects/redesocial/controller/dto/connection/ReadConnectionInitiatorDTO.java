package com.renato.projects.redesocial.controller.dto.connection;

import com.renato.projects.redesocial.domain.Connection;

public record ReadConnectionInitiatorDTO(
		Long id,
		String username,
		String photo,
		String name,
		String nickname) {

	public ReadConnectionInitiatorDTO(Connection connection) {
		this(connection.getId(),
				connection.getInitiator().getUserAccount().getUsername(),
				connection.getInitiator().getPhotoUrl(),
				connection.getInitiator().getName(),
				connection.getInitiator().getNickname());
		
	}
}
