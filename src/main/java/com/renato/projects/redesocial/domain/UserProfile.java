package com.renato.projects.redesocial.domain;

import java.time.LocalDate;

import com.renato.projects.redesocial.domain.enums.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String name;
	private String nickname;
	private LocalDate birthDate;
	private String photoUrl;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private Boolean active;
	private String bio;
	@OneToOne
	@JoinColumn(name = "user_account_id", unique = true)
	private UserAccount userAccount;

	public UserProfile(String name, String nickname, LocalDate birthDate, Gender gender, UserAccount userAccount) {
		this.name = name;
		this.nickname = nickname;
		this.birthDate = birthDate;
		this.gender = gender;
		this.userAccount = userAccount;
		this.active = true;
	}
}
