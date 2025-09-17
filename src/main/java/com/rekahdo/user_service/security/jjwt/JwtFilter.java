package com.rekahdo.user_service.security.jjwt;

import com.rekahdo.user_service.security.config.Api_UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Lazy
public class JwtFilter extends OncePerRequestFilter {

	private final JwtSymmetricService jwtService;
	private final Api_UserDetailsService apiUserDetailsService;

	@Autowired
	public JwtFilter(JwtSymmetricService jwtService, Api_UserDetailsService apiUserDetailsService) {
		this.jwtService = jwtService;
		this.apiUserDetailsService = apiUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null, username = null;

		// authHeader would contain "Bearer encoded-token"
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
		}

		// If username is not authenticated, SecurityContextHolder Context Authentication would be null
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
			UserDetails userDetails = apiUserDetailsService.loadUserByUsername(username);

			if(jwtService.isTokenValid(token, userDetails)){
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}

}
