package com.renato.projects.redesocial.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.redesocial.controller.dto.connection.AcceptConnectionDTO;
import com.renato.projects.redesocial.controller.dto.connection.PostConnectionDTO;
import com.renato.projects.redesocial.controller.dto.connection.ReadConnectionDTO;
import com.renato.projects.redesocial.controller.dto.connection.ReadConnectionInitiatorDTO;
import com.renato.projects.redesocial.domain.Connection;
import com.renato.projects.redesocial.service.ConnectionService;

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
		System.out.println("post connection aqui");
		connectionService.postConnection(postConnectionDTO);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getConnectionBetweenTargetAndAuthenticated(@PathVariable Long id){
		Connection connection = connectionService.getConnection(id);
		return ResponseEntity.ok(new ReadConnectionDTO(connection));
	}
	
	@GetMapping("/invites")
	public ResponseEntity<?> getPendingConnections(){
		List<Connection> connections = connectionService.getPendingConnections();
		return ResponseEntity.ok(
				connections.stream().map(connection -> new ReadConnectionInitiatorDTO(connection))
				.collect(Collectors.toList())
				);
	}
	
	@PostMapping("/invites")
	public ResponseEntity<?> acceptConnection(@RequestBody AcceptConnectionDTO acceptConnectionDTO){
		connectionService.acceptConnection(acceptConnectionDTO);
		return ResponseEntity.ok().build();
	}
}
