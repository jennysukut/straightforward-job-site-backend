package com.sfjs.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.gql.schema.Result;
import com.sfjs.security.JwtTokenUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@EnableWebMvc
@Transactional
public class AuthMutationResolver {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private HttpServletResponse response;
  
  @MutationMapping(name = "login")
  public Result login(@Argument(name = "email") String email, @Argument(name = "password") String password) {
    Result result = new Result();

    // Create the authentication object
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, password));

    if (authentication.isAuthenticated()) {
      // Add the authentication object to the security context
      SecurityContextHolder.getContext().setAuthentication(authentication);
      result.setSuccess(true);
      // Generate a JWT token for future requests
      String token = jwtTokenUtil.generateToken(email, authentication.getAuthorities());
      response.setHeader("Authorization", "Bearer " + token);
    } else {
      result.setSuccess(false);
    }

    return result;
  }
}
