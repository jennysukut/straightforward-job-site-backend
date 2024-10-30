package com.sfjs.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationService extends OncePerRequestFilter {

  private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

  @Value("${api.key}")
  private String API_KEY;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);

    if (apiKey == null || !apiKey.equals(API_KEY)) {
      throw new BadCredentialsException("Invalid API Key");
    }

    filterChain.doFilter(request, response);
  }
}
