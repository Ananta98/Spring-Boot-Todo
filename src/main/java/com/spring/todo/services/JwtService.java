package com.spring.todo.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value(value = "${security.jwt.secret-key}")
	private String secretKey;
	
	@Value(value = "${security.jwt.expiration-time}")
	private long expirationTime;
	
	public String extractEmail(String token) {
		Claims extracted = extractAllTokens(token);
		return extracted.getSubject();
	}
	
	public Date extractExpirationDate(String token) {
		Claims extracted = extractAllTokens(token);
		return extracted.getExpiration();
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> extraClaims = new HashMap<>();
		return buildToken(extraClaims, userDetails, expirationTime);
	}
	
	public boolean isValidToken(String token, UserDetails userDetails) {
		String extractedEmail = extractEmail(token);
		Date currentDate = new Date(System.currentTimeMillis());
		Date expiredDate = extractExpirationDate(token);
		return userDetails.getUsername().equals(extractedEmail) && currentDate.before(expiredDate);
	}
	
	public long getExpirationTime() {
		return expirationTime;
	}
	
	private String buildToken(
		Map<String, Object> extraClaims,
		UserDetails userDetails,
		long expiration
	) {
		Date currentDate = new Date(System.currentTimeMillis());
		Date expiredDate = new Date(System.currentTimeMillis() + expiration);
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(currentDate)
				.setExpiration(expiredDate)
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Claims extractAllTokens(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
