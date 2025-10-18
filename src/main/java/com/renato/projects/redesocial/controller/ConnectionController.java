package com.renato.projects.redesocial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.redesocial.controller.dto.connection.PostConnectionDTO;
import com.renato.projects.redesocial.controller.dto.connection.ReadConnectionDTO;
import com.renato.projects.redesocial.domain.Connection;
import com.renato.projects.redesocial.service.ConnectionService;
import com.renato.projects.redesocial.service.NotificationService;

@RestController
@RequestMapping("/connection")
public class ConnectionController {

	private ConnectionService connectionService;
	
	public ConnectionController(ConnectionService connectionService) {
		super();
		this.connectionService = connectionService;
	}

	@PostMapping
	public ResponseEntity<?> postConnection(@RequestBody PostConnectionDTO postConnectionDTO){
		connectionService.postConnection(postConnectionDTO);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getConnection(@PathVariable Long id){
		Connection connection = connectionService.getConnection(id);
		return ResponseEntity.ok(new ReadConnectionDTO(connection));
	}
}
