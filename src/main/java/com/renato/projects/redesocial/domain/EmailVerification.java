package com.renato.projects.redesocial.domain;

import java.time.LocalDateTime;

import com.renato.projects.redesocial.controller.exceptionhandler.EmailVerificationExpiredException;
import com.renato.projects.redesocial.utils.GerarCodigoConfirmacaoEmail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmailVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String codigo;
	private LocalDateTime timeGeracaoCod;
	@OneToOne(mappedBy = "emailVerification")
	private UserAccount user;

	public EmailVerification() {
		super();
		this.gerarNovoCodigo();		
	}

	public void gerarNovoCodigo() {
		this.codigo = GerarCodigoConfirmacaoEmail.gerarCodigo();
		this.timeGeracaoCod = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}

	public boolean isExpirado() {
		return LocalDateTime.now().isAfter(this.timeGeracaoCod.plusMinutes(30));
	}
	
	public String getCodigo() {
		if (isExpirado()) {
			throw new EmailVerificationExpiredException();
		}
		return this.codigo;
	}
}
