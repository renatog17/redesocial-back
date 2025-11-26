package com.renato.projects.redesocial.controller.dto.comment;

import com.renato.projects.redesocial.domain.Comment;

public record PostCommentDTO(
		String content
		) {
	
	public Comment toModel() {
		return new Comment(content);
	}

}
