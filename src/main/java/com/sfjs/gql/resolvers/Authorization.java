package com.sfjs.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.security.AuthorizationService;

@RestController
@EnableWebMvc
@Transactional
public class Authorization {

  @Autowired
  private AuthorizationService authorizationService;

  @MutationMapping(name = "login")
  public void login(@Argument(name = "email") String email, @Argument(name = "password") String password) {
    authorizationService.login(email, password);
  }

  @MutationMapping(name = "resetPassword")
  public void resetPassword(@Argument(name = "email") String email, @Argument(name = "password") String password,
      @Argument(name = "token") String token) {
    authorizationService.resetPassword(email, password, token);
  }

  @MutationMapping(name = "generateResetPasswordToken")
  public String generateResetPasswordToken(@Argument(name = "email") String email) {
    return authorizationService.generateResetPasswordToken(email);
  }
}
