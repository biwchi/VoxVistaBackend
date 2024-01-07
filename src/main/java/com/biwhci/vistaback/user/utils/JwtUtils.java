package com.biwhci.vistaback.user.utils;

import com.biwhci.vistaback.user.models.AppUser;
import com.biwhci.vistaback.user.services.AppUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    private final AppUserService appUserService;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        AppUser user = appUserService.findByEmail(userDetails.getUsername())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        claims.put("id", user.getUserId());
        claims.put("email", user.getEmail());
        claims.put("nickname", user.getNickname());
        claims.put("roles", user.getAuthorities());

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + jwtLifetime.toMillis());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .signWith(getSigningKey())
                .compact();
    }

    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).get("email", String.class);
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] bytes = secret.getBytes();
        return Keys.hmacShaKeyFor(bytes);
    }
}
