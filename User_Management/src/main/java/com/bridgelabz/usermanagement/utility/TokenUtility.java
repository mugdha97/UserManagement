package com.bridgelabz.usermanagement.utility;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class TokenUtility {
  
	public String generateToken(String userMail ) {
		String token = Jwts.builder().setSubject(String.valueOf(userMail)).signWith(SignatureAlgorithm.HS256, "userMail")
				.compact();
		return token;
	}
	
	public static String generateUserFromToken(String token) {
		Claims claim = Jwts.parser().setSigningKey("userMail").parseClaimsJws(token).getBody();
		String email = claim.getSubject();
		return email;
		
	}
}
