package com.renato.projects.redesocial.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.renato.projects.redesocial.controller.dto.userprofile.ReadPrivateUserProfileDTO;
import com.renato.projects.redesocial.controller.dto.userprofile.ReadUserProfileDTO;
import com.renato.projects.redesocial.domain.Connection;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.domain.enums.ConnectionStatus;
import com.renato.projects.redesocial.domain.enums.Visibility;
import com.renato.projects.redesocial.repository.ConnectionRepository;
import com.renato.projects.redesocial.repository.UserProfileRepository;

@Service
public class UserProfileService {

	UserProfileRepository userProfileRepository;
	ConnectionRepository connectionRepository;
	UserAccountService userAccountService;

	public UserProfileService(UserProfileRepository userProfileRepository, ConnectionRepository connectionRepository,
			UserAccountService userAccountService) {
		super();
		this.userProfileRepository = userProfileRepository;
		this.connectionRepository = connectionRepository;
		this.userAccountService = userAccountService;
	}
	
	public UserProfile findByid(Long id) {
		return userProfileRepository.findById(id).orElseThrow( () -> new NoSuchElementException("User not found"));
	}
	
	public ResponseEntity<?> findByNickname(String nickname) {
	    UserProfile targetUserProfile = userProfileRepository.findByNickname(nickname)
	            .orElseThrow(() -> new NoSuchElementException("User not found"));
	    UserProfile currentUserProfile = userAccountService.getCurrentUserProfile();
	    Optional<Connection> connectionOptional = connectionRepository
	            .findByInitiatorIdAndFriendId(currentUserProfile.getId(), targetUserProfile.getId());

	    if(connectionOptional.isPresent() && connectionOptional.get().getStatus() == ConnectionStatus.BLOCKED) {
	    	throw new NoSuchElementException("User not found");
	    }
	    
	    if (targetUserProfile.getVisibility() == Visibility.PUBLIC) {
	        return ResponseEntity.ok(new ReadUserProfileDTO(targetUserProfile));
	    }
	    
	    if (targetUserProfile.getVisibility() == Visibility.FRIENDS_ONLY) {
	        if (connectionOptional.isEmpty()) {
	        	return ResponseEntity.ok(new ReadPrivateUserProfileDTO(targetUserProfile));
	        }
	        Connection connection = connectionOptional.get();
	        if(connection.getStatus() == ConnectionStatus.PENDING)
	        	return ResponseEntity.ok(new ReadPrivateUserProfileDTO(targetUserProfile));
	        }else {
	        	return ResponseEntity.ok(new ReadUserProfileDTO(targetUserProfile));
	    }
	    return ResponseEntity.ok(new ReadPrivateUserProfileDTO(targetUserProfile));
	}
}
