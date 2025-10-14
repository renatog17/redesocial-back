package com.renato.projects.redesocial.controller.exceptionhandler;

public class EmailVerificationExpiredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailVerificationExpiredException() {
        super("O código expirou. Solicite um novo código.");
    }
}
