package com.sfjs.gql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.crud.request.BusinessRequest;
import com.sfjs.crud.request.FellowRequest;
import com.sfjs.crud.response.FellowResponse;
import com.sfjs.crud.svc.FellowService;
import com.sfjs.dto.Result;
import com.sfjs.gql.svc.SignupService;

@RestController
@EnableWebMvc
@Transactional
public class Signup {

  @Autowired
  private FellowService fellowService;

  @Autowired
  private SignupService signupService;

  @MutationMapping(name = "signupBusiness")
  public Long signupBusiness(@Argument(name = "requestBody") BusinessRequest requestBody) {
    return signupService.signupBusiness(requestBody).getId();
  }

  @MutationMapping(name = "signupFellow")
  public Long signupFellow(@Argument(name = "requestBody") FellowRequest requestBody) {
    return signupService.signupFellow(requestBody).getId();
  }

  @Deprecated
  @MutationMapping(name = "signUp")
  public Result signupIndividual(@Argument(name = "name") String name, @Argument(name = "email") String email,
      @Argument(name = "betaTester") Boolean betaTester) {
    FellowRequest fellow = new FellowRequest();
    fellow.setName(name);
    fellow.setEmail(email);
    fellow.setBetaTester(betaTester);
    FellowResponse fellowResponse = signupService.signupFellow(fellow);
    Result result = new Result();
    result.setSuccess(true);
    result.setMessage("Success");
    return result;
  }
}
