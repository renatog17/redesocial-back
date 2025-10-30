package com.renato.projects.redesocial.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.renato.projects.redesocial.aws.S3Service;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.service.UserAccountService;
import com.renato.projects.redesocial.service.UserProfileService;

import jakarta.transaction.Transactional;

@RequestMapping("/user/profile")
@RestController
public class UserProfileController {

	private UserProfileService userProfileService;
	private S3Service s3Service;
	private UserAccountService userAccountService;
	public UserProfileController(UserProfileService userProfileService, S3Service s3Service,
			UserAccountService userAccountService) {
		super();
		this.userProfileService = userProfileService;
		this.userAccountService = userAccountService;
		this.s3Service = s3Service;
	}

	@GetMapping("/{nickname}")
	public ResponseEntity<?> getUserProfileByNickname(@PathVariable String nickname) {
		return ResponseEntity.ok(userProfileService.findByNickname(nickname));
	}
	
	@PostMapping("/photo")
	@Transactional
	public void uploadProfilePhoto(@RequestParam("file") MultipartFile file) throws IOException{
		File tempFile = convertToFile(file);
		String uniqueId = UUID.randomUUID().toString();
		UserProfile currentUserProfile = userAccountService.getCurrentUserProfile();
		
	    String key = "profiles/" + currentUserProfile.getNickname() + "_" + uniqueId + "_" + file.getOriginalFilename();
	    currentUserProfile.setPhotoUrl("https://s3-rede-social.s3.amazonaws.com/" + key);
	    userProfileService.save(currentUserProfile);
	    
	    s3Service.uploadFile(key, tempFile);
	    tempFile.delete();
	}
	
	private File convertToFile(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("upload-", multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }
}
