package com.renato.projects.redesocial.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.renato.projects.redesocial.repository.UserAccountRepository;
import com.renato.projects.redesocial.security.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	TokenService tokenService;
	@Autowired
	UserAccountRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = this.recoverToken(request);
		if(token!=null) {
			var login = tokenService.validateToken(token);
			UserDetails user = userRepository.findByUserName(login).get();
			var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
	
	//agora a autenticação é por cookies
//	private String recoverToken(HttpServletRequest request) {
//		var authHeader = request.getHeader("Authorization");
//		if(authHeader == null)
//			return null;
//		return authHeader.replace("Bearer ", "");
//
//	}
	
	private String recoverToken(HttpServletRequest request) {
	    if (request.getCookies() == null) return null;

	    for (var cookie : request.getCookies()) {
	        if (cookie.getName().equals("token")) {
	            return cookie.getValue();
	        }
	    }

	    return null;
	}
}