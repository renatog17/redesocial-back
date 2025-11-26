package com.renato.projects.redesocial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.redesocial.service.VisualizationService;

@RestController
@RequestMapping("/visualization")
public class VisualizationController {

	public VisualizationService visualizationService;

	public VisualizationController(VisualizationService visualizationService) {
		super();
		this.visualizationService = visualizationService;
	}
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<?> postVisualization(@PathVariable Long postId) {
		visualizationService.postVisualization(postId);
		return ResponseEntity.ok().build();
	}
}
