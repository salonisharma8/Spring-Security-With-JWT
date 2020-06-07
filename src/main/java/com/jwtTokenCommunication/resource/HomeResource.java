package com.jwtTokenCommunication.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwtTokenCommunication.DTO.AuthenticationRequest;
import com.jwtTokenCommunication.DTO.AuthenticationResponse;
import com.jwtTokenCommunication.service.LogInUserService;
import com.jwtTokenCommunication.utility.JWTTokenUtility;

@RestController
public class HomeResource {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenUtility jwtTokenUtility;

	@Autowired
	private LogInUserService logInUserService;

	@GetMapping("/user")
	public String user() {
		return ("<h1> Bonjour User </h1>");
	}

	@GetMapping("/admin")
	public String admin() {
		return ("<h1> Bonjour Admin </h1>");
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = logInUserService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwtTokenString = jwtTokenUtility.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwtTokenString));
	}
}
