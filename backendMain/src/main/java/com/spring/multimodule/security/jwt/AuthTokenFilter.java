package com.spring.multimodule.security.jwt;

import com.spring.multimodule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		try{
			String jwt = parseJwt(httpServletRequest);

			if (jwt != null && jwtUtils.validateJwtToken(jwt)){
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				UserDetails user = userService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						user, null, user.getAuthorities()
				);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		catch (Exception ex){
			System.err.println(ex.getMessage());
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private String parseJwt(HttpServletRequest request){
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")){
			return headerAuth.substring(7);
		}
		return null;
	}
}
