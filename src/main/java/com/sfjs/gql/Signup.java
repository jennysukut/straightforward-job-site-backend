package com.sfjs.gql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.dto.Business;
import com.sfjs.dto.Fellow;
import com.sfjs.dto.Result;
import com.sfjs.svc.BusinessService;
import com.sfjs.svc.FellowService;

@RestController
@EnableWebMvc
@Transactional
public class Signup {

  @Autowired
  private BusinessService businessService;

  @Autowired
  private FellowService fellowService;

  @MutationMapping(name = "signupBusiness")
  public Long signupBusiness(@Argument(name = "requestBody") Business requestBody) {
    return businessService.customSave(requestBody).getId();
  }

  @MutationMapping(name = "signupFellow")
  public Long signupFellow(@Argument(name = "requestBody") Fellow requestBody) {
    return fellowService.customSave(requestBody).getId();
  }

  @Deprecated
  @MutationMapping(name = "signUp")
  public Result signupIndividual(@Argument(name = "name") String name,
      @Argument(name = "email") String email,
      @Argument(name = "betaTester") Boolean betaTester) {
    Fellow fellow = new Fellow();
    fellow.setName(name);
    fellow.setEmail(email);
    fellow.setBetaTester(betaTester);
    fellow = fellowService.customSave(fellow);
    Result result = new Result();
    result.setSuccess(true);
    result.setMessage("Success");
    return result;
  }
}
