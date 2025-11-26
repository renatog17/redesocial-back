package com.renato.projects.redesocial.controller.dto.comment;

import java.time.LocalDateTime;

import com.renato.projects.redesocial.domain.Comment;

public record ReadCommentDTO(
		String content,
		LocalDateTime date,
		String nickNameAutor,
		String nameAutor,
		String photoUrlAutor
		) {

	public ReadCommentDTO(Comment comment) {
		this(comment.getContent(), comment.getDate(), comment.getUserProfile().getNickname(),
				comment.getUserProfile().getName(), comment.getUserProfile().getPhotoUrl());
	}
}
