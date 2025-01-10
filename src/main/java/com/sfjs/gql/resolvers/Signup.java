package com.sfjs.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.data.BusinessProfileData;
import com.sfjs.data.ExtendedProfileData;
import com.sfjs.gql.schema.BusinessInput;
import com.sfjs.gql.schema.FellowInput;
import com.sfjs.gql.svc.SignupService;

import graphql.schema.DataFetchingEnvironment;

@RestController
@Transactional
public class Signup {

  @Autowired
  private SignupService signupService;

  @MutationMapping(name = "signupBusiness")
  public Long signupBusiness(@Argument(name = "requestBody") BusinessInput requestBody) {
    return signupService.signupBusiness(requestBody);
  }

  @MutationMapping(name = "signupFellow")
  public Long signupFellow(@Argument(name = "requestBody") FellowInput requestBody) {
    return signupService.signupFellow(requestBody);
  }

  @MutationMapping(name = "saveProfile")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public ExtendedProfileData saveProfile(@Argument(name = "requestBody") ExtendedProfileData requestBody,
      DataFetchingEnvironment environment) throws Exception {
    return signupService.saveProfile(requestBody, environment);
  }

  @QueryMapping(name = "fellowProfile")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public ExtendedProfileData fellowProfile() {
    return signupService.getFellowProfile();
  }

  @MutationMapping(name = "saveBusinessProfile")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public BusinessProfileData saveBusinessProfile(@Argument(name = "requestBody") BusinessProfileData requestBody,
      DataFetchingEnvironment environment) throws Exception {
    return signupService.saveBusinessProfile(requestBody, environment);
  }

}
