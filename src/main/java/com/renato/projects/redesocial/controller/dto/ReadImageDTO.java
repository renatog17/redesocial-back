package com.renato.projects.redesocial.controller.dto;

import com.renato.projects.redesocial.domain.Image;

public record ReadImageDTO(
		Long id,
		String url){

	public ReadImageDTO(Image image) {
		this(image.getId(), image.getUrl());
	}
}
