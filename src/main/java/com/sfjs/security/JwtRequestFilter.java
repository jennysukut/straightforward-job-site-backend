package com.sfjs.security;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.sfjs.data.entity.AccountEntity;
import com.sfjs.jpa.repo.AccountRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private AccountRepository accountRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    logger.entering(this.getClass().getName(), "doFilterInternal");

    // TODO Verify if we can remove this and then remove it
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      chain.doFilter(request, response);
      return;
    }

    // Get the authorization header
    final String requestTokenHeader = request.getHeader("Authorization");
    logger.info("Authorization header: " + requestTokenHeader);

    // If there is no proper header, move on to next request filter
    if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    // Get the token
    String jwtToken = requestTokenHeader.substring(7);

    // Check if the token is valid
    boolean isValid = false;
    try {
      isValid = jwtTokenUtil.validateToken(jwtToken);
    } catch (TokenExpiredException ex) {
      // If the token has expired, move on to next request filter
      logger.warning(ex.getMessage());
      chain.doFilter(request, response);
      return;
    }

    // If the token is valid...
    if (isValid) {
      // get the email and authorities
      String email = jwtTokenUtil.getSubjectFromToken(jwtToken);
      AccountEntity accountEntity = accountRepository.findByEmail(email);
      if (accountEntity != null) {
        Collection<? extends GrantedAuthority> authorities = accountEntity.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getReference())).collect(Collectors.toList());
        // Create the authentication object
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, null, authorities);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);
      }
    }

    // Move on to next filter
    chain.doFilter(request, response);
  }
}
