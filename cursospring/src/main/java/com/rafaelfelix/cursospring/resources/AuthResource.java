package com.rafaelfelix.cursospring.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelfelix.cursospring.dto.EmailDTO;
import com.rafaelfelix.cursospring.security.JWTUtil;
import com.rafaelfelix.cursospring.security.UserSS;
import com.rafaelfelix.cursospring.services.AuthService;
import com.rafaelfelix.cursospring.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;

	@PostMapping(value = "/refresh_token")
	public ResponseEntity<?> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/forgot")
	public ResponseEntity<?> getNewPassword(@Valid @RequestBody EmailDTO emailDTO) {
		authService.sendNewPassword(emailDTO.getEmail());
		
		return ResponseEntity.noContent().build();
	}

}
