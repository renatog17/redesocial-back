package com.renato.projects.redesocial.controller.dto.connection;

import java.time.LocalDateTime;

import com.renato.projects.redesocial.domain.Connection;
import com.renato.projects.redesocial.domain.enums.ConnectionStatus;

public record ReadConnectionDTO(Long id,
		LocalDateTime acceptedAt,
		ConnectionStatus status) {

	public ReadConnectionDTO(Connection connection) {
		this(connection.getId(), connection.getAcceptedAt(), connection.getStatus());
	}
}
