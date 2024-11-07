package com.sfjs.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  private Algorithm algorithm;
  private JWTVerifier verifier;

  @PostConstruct
  public void init() {
    algorithm = Algorithm.HMAC256(secret);
    verifier = JWT.require(algorithm).withIssuer(secret).build();
  }

  public Date getExpirationDateFromToken(String token) {
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT.getExpiresAt();
  }

  public Claim getClaimFromToken(String token, String key) {
    final Map<String, Claim> claims = getAllClaimsFromToken(token);
    return claims.get(key);
  }

  private Map<String, Claim> getAllClaimsFromToken(String token) {
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT.getClaims();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(String email, Collection<? extends GrantedAuthority> authorities) {
    List<String> authoritiesList = authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    String jwtToken = JWT.create()
        .withIssuer(secret)
        .withSubject(email)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
        .withJWTId(UUID.randomUUID().toString())
        .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
        .withClaim("authorities", authoritiesList)
        .sign(algorithm);
    return jwtToken;
  }

  public String getSubjectFromToken(String token) {
    DecodedJWT decodedJWT = verifier.verify(token);
    return decodedJWT.getSubject();
  }

  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

  public Collection<? extends GrantedAuthority> getAuthorities(String token) {
    DecodedJWT decodedJWT = verifier.verify(token);
    List<String> authoritiesList = decodedJWT.getClaim("authorities").asList(String.class);
    return authoritiesList.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
