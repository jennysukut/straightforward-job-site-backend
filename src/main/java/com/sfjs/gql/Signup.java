package com.sfjs.gql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.dto.Business;
import com.sfjs.svc.BusinessService;

@RestController
@EnableWebMvc
@Transactional
public class Signup {

  @Autowired
  private BusinessService businessService;

  @MutationMapping(name = "signupBusiness")
  public Long signupBusiness(@Argument(name = "requestBody") Business requestBody) {
    return businessService.customSave(requestBody).getId();
  }
}
