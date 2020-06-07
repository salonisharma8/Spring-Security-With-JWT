package com.jwtTokenCommunication.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.jwtTokenCommunication.service.LogInUserService;
import com.jwtTokenCommunication.utility.JWTTokenUtility;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	private LogInUserService logInUserService;

	@Autowired
	private JWTTokenUtility jwtTokenUtility;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		String username = null, jwtTokenString = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			
			jwtTokenString = authorizationHeader.substring(7);
			
			username = jwtTokenUtility.extractUsername(jwtTokenString);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.logInUserService.loadUserByUsername(username);

			if (jwtTokenUtility.validateToken(jwtTokenString, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
