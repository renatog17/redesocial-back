package com.renato.projects.redesocial.domain;

import java.time.LocalDateTime;

import com.renato.projects.redesocial.domain.enums.ConnectionStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToOne
	@JoinColumn(name = "initiator_id", nullable = false)
	private UserProfile initiator; // quem enviou a solicitação

	@ManyToOne
	@JoinColumn(name = "friend_id", nullable = false)
	private UserProfile friend; // quem recebeu a solicitação

	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime acceptedAt;

	@Enumerated(EnumType.STRING)
	private ConnectionStatus status = ConnectionStatus.PENDING;

	private Boolean isFavorite = false;
	private String notes;

	public Connection(UserProfile initiator, UserProfile friend) {
		this.initiator = initiator;
		this.friend = friend;
		this.status = ConnectionStatus.PENDING;
		this.createdAt = LocalDateTime.now();
	}
}
