package com.rekahdo.user_service.security.jjwt;

import com.rekahdo.user_service.dtos.records.JJwtResponse;
import com.rekahdo.user_service.enums.AuthorityRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Service
@Getter
public class JwtSymmetricService {

    // Add this to expose the secret key for verification (DEV ONLY)
    private final String secretKey;

	public JwtSymmetricService() {
		// Generate key once during initialization
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey genSK = keyGen.generateKey();
			this.secretKey = Base64.getUrlEncoder().withoutPadding().encodeToString(genSK.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Failed to generate secret key", e);
		}
	}

	public JJwtResponse createToken(Authentication authentication) {
		return new JJwtResponse(create(authentication), getSecretKey());
	}

	private String create(Authentication authentication) {
		Map<String, Object> claims = new HashMap<>(Map.of(
				"isAdmin", getScope(authentication).contains("ROLE_" + AuthorityRole.ADMIN),
				"scope", getScope(authentication)
		));

		return Jwts.builder()
				.claims()
				.subject(authentication.getName())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusSeconds(60 * 60)))
				.issuer("self")
				.add(claims)
				.and()
				.signWith(getKey())
				.compact();
	}

	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private List<String> getScope(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
	}

    public String extractUsername(String token) {
		// extract the username from jwt token
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUsername(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}