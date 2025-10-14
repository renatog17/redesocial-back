package com.renato.projects.redesocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.redesocial.domain.UserAccount;

public interface UserProfileRepository extends JpaRepository<UserAccount, Long>{


}