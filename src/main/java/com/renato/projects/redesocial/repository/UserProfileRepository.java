package com.renato.projects.redesocial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.redesocial.domain.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{

	Optional<UserProfile> findByNickname(String nickname);
}