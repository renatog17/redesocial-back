package com.renato.projects.redesocial.domain;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.renato.projects.redesocial.domain.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity()
public class UserAccount implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String userName;
	private String password;
	private UserRole role;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "email_verification_id", referencedColumnName = "id")
	private EmailVerification emailVerification;
	private Boolean confirmacaoEmail;
	@OneToOne(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserProfile profile;

	public EmailVerification getEmailVerification() {
		return emailVerification;
	}

	public UserAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAccount(String email, String password) {
		this.emailVerification = new EmailVerification();
		this.userName = email;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.role = UserRole.USER;
		this.confirmacaoEmail = false;
	}

	public Boolean getConfirmacaoEmail() {
		return confirmacaoEmail;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public UserRole getRole() {
		return role;
	}
	
	public UserProfile getProfile() {
		return profile;
	}

	public void confirmarEmail() {
		this.confirmacaoEmail = true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UserRole.ADMIN)
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		else
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}