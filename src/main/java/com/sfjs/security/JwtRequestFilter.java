package com.sfjs.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    // TODO Verify if we can remove this and then remove it
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      chain.doFilter(request, response);
      return;
    }

    // Get the authorization header
    final String requestTokenHeader = request.getHeader("Authorization");

    // If there is no proper header, move on to next request filter
    if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }

    // Get the token
    String jwtToken = requestTokenHeader.substring(7);

    // Check if the token is valid
    boolean isValid = jwtTokenUtil.validateToken(jwtToken);

    // If the token is valid...
    if (isValid) {
      // get the email and authorities
      String email = jwtTokenUtil.getSubjectFromToken(jwtToken);  
      Collection<? extends GrantedAuthority> authorities = jwtTokenUtil.getAuthorities(jwtToken);
      // Create the authentication object
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, null, authorities);
      token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(token);
    }

    // Move on to next filter
    chain.doFilter(request, response);
  }
}
