package com.renato.projects.redesocial.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.renato.projects.redesocial.aws.S3Service;
import com.renato.projects.redesocial.controller.dto.post.PostPostDTO;
import com.renato.projects.redesocial.controller.dto.post.ReadPostDTO;
import com.renato.projects.redesocial.domain.Image;
import com.renato.projects.redesocial.domain.Post;
import com.renato.projects.redesocial.domain.UserProfile;
import com.renato.projects.redesocial.repository.ImageRepository;
import com.renato.projects.redesocial.repository.PostRepository;
import com.renato.projects.redesocial.service.PostService;
import com.renato.projects.redesocial.service.UserAccountService;
import com.renato.projects.redesocial.utils.MultPartFileToFile;

@RequestMapping("/post")
@RestController
public class PostController {

	private PostService postService;
	private PostRepository postRepository;
	private ImageRepository imageRepository;
	private UserAccountService userAccountService;
	private S3Service s3Service;
	
	public PostController(PostService postService, UserAccountService userAccountService,
			PostRepository postRepository, ImageRepository imageRepository,
			S3Service s3Service) {
		super();
		this.postService = postService;
		this.userAccountService = userAccountService;
		this.imageRepository = imageRepository;
		this.postRepository = postRepository;
		this.s3Service = s3Service;
	}

	@PostMapping()
	public ResponseEntity<?> createPost(@RequestBody PostPostDTO postPostDTO, UriComponentsBuilder uriComponentsBuilder){
		Post post = postService.save(postPostDTO);
		URI uri = uriComponentsBuilder.path("/post/{id}").buildAndExpand(post.getId()).toUri();
		System.out.println("aqui no create post");
		return ResponseEntity.created(uri).body(new ReadPostDTO(post));
	}
	
	@PostMapping("/photo")
	public void uploadImageCreatePost(@RequestParam("file") MultipartFile file,
			@RequestParam("postId") Long postId) throws IOException{
		File tempFile = MultPartFileToFile.convertToFile(file);
		String uniqueId = UUID.randomUUID().toString();

		UserProfile currentUserProfile = userAccountService.getCurrentUserProfile();
		
		Post post = postService.getPost(postId);
		String key = "posts/" + currentUserProfile.getId() + "_" + uniqueId + "_" + file.getOriginalFilename();
		s3Service.uploadFile(key, tempFile);
		Image image = new Image();
		image.setUrl("https://s3-rede-social.s3.amazonaws.com/"+key);
		image.setPost(post);
		
		imageRepository.save(image);
		post.getImages().add(image);
		postRepository.save(post);
	}
	
	@GetMapping
	public ResponseEntity<?> getPosts(){
		List<ReadPostDTO> postsDTO = postService.getPosts();
		return ResponseEntity.ok(postsDTO);
	}
}
