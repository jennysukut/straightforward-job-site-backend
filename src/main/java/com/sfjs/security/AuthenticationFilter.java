package com.sfjs.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private AuthenticationService authenticationService;

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    try {
      Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception exp) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
      PrintWriter writer = httpResponse.getWriter();
      writer.print(exp.getMessage());
      writer.flush();
      writer.close();
    }

    filterChain.doFilter(request, response);
  }
}
