package com.sfjs.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.gql.schema.BusinessInput;
import com.sfjs.gql.schema.FellowInput;
import com.sfjs.gql.schema.Result;
import com.sfjs.gql.svc.SignupService;

@RestController
@EnableWebMvc
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

  @Deprecated
  @MutationMapping(name = "signUp")
  public Result signupIndividual(@Argument(name = "name") String name, @Argument(name = "email") String email,
      @Argument(name = "betaTester") Boolean betaTester) {
    FellowInput fellow = new FellowInput();
    fellow.setName(name);
    fellow.setEmail(email);
    fellow.setBetaTester(betaTester);
    signupService.signupFellow(fellow);
    Result result = new Result();
    result.setSuccess(true);
    result.setMessage("Success");
    return result;
  }
}
