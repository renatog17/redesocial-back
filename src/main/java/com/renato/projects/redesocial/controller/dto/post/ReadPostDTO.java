package com.renato.projects.redesocial.controller.dto.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.renato.projects.redesocial.controller.dto.ReadImageDTO;
import com.renato.projects.redesocial.controller.dto.comment.ReadCommentDTO;
import com.renato.projects.redesocial.domain.Post;

public record ReadPostDTO(
        Long id, 
        LocalDateTime createdAt, 
        String content, 
        Long likes, 
        List<ReadImageDTO> images,
        String userProfileName,
        String userProfileNickName, 
        String userProfilePhotoURL,
        List<ReadCommentDTO> comments
) {

    public ReadPostDTO(Post post) {
        this(
            post.getId(),
            post.getCreatedAt(),
            post.getContent(),
            (long) post.getLikesList().size(), // cast para Long
            post.getImages()
                .stream()
                .map(ReadImageDTO::new)
                .collect(Collectors.toList()),
            post.getUserProfile().getName(),
            post.getUserProfile().getNickname(),
            post.getUserProfile().getPhotoUrl(),
            post.getComments()
                .stream()
                .map(ReadCommentDTO::new)
                .collect(Collectors.toList())
        );
    }
}
