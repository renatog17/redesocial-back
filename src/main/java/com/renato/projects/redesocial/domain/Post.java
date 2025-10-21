package com.renato.projects.redesocial.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private LocalDateTime createdAt;
	private String content;
	private Long likes;
	@ManyToOne
	@JoinColumn(name = "user_profile_id")
	private UserProfile userProfile;
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Image> images;
	
	public Post(String content, List<String> imgs) {
		this.content = content;
		this.likes = 0L;
		this.images = new ArrayList<Image>();
		this.createdAt = LocalDateTime.now();
		if (imgs != null) {
	        for (String url : imgs) {
	            Image image = new Image();
	            image.setUrl(url);
	            image.setPost(this);
	            this.images.add(image);
	        }
	    }
	}
}
