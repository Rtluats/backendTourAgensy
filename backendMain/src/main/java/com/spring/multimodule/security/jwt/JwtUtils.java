package com.spring.multimodule.security.jwt;

import com.spring.multimodule.dto.UserDto;
import com.spring.multimodule.entity.User;
import com.spring.multimodule.mapper.UserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
	@Autowired
	private UserMapper mapper;

	@Value("${valhirr.app.jwtSecret}")
	private String jwtSecret;

	@Value("${valhirr.app.jwtExpirationMs}")
	private int jwtExpirationMS;

	public String generateJwtToken(Authentication auth){
		UserDto userPrincipal =  mapper.toDto(auth.getPrincipal());
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMS))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token){
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken){
		try{
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		}catch (Exception exception){
			System.err.println(exception.getMessage());
		}

		return false;
	}

}
