package com.renato.projects.redesocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.redesocial.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
