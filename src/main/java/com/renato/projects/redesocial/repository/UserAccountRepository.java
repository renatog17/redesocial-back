package com.renato.projects.redesocial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.redesocial.domain.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{

	//UserDetails findByLogin(String login);
	Optional<UserAccount> findByUserName(String userName);

	Boolean existsByUserName(String login);
}