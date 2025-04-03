package com.sfjs.gql.resolvers;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.sfjs.gql.svc.BusinessService;
import com.sfjs.gql.svc.FellowService;

@RestController
@Transactional
public class Signup {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private BusinessService businessService;

  @Autowired
  private FellowService fellowService;

  @MutationMapping(name = "signupBusiness")
  public Long signupBusiness(
      @Argument(name = "email") String email,
      @Argument(name = "password") String password,
      @Argument(name = "name") String name,
      @Argument(name = "isBetaTester") Optional<Boolean> isBetaTester,
      @Argument(name = "contactName") String contactName,
      @Argument(name = "isEarlySignup") Optional<Boolean> isEarlySignup,
      @Argument(name = "referral") String referral) {
    return businessService.businessSignup(email, password, name, isBetaTester, contactName, isEarlySignup, referral)
        .map(businessEntity -> businessEntity.getId())
        .orElseThrow(() -> new InternalError());
  }

  @MutationMapping(name = "signupFellow")
  public Long signupFellow(
      @Argument(name="email") String email,
      @Argument(name="password") String password,
      @Argument(name="name") String name,
      @Argument(name="isBetaTester") Optional<Boolean> isBetaTester,
      @Argument(name="isCollaborator") Optional<Boolean> isCollaborator,
      @Argument(name="message") String message,
      @Argument(name="referralCode") String referralCode,
      @Argument(name="isReferralPartner") Optional<Boolean> isReferralPartner) {
    logger.info("Entering Signup::signupFellow");
    return fellowService.fellowSignup(email, password, name, isBetaTester, isCollaborator, message, referralCode, isReferralPartner)
        .map(fellowEntity -> fellowEntity.getId())
        .orElseThrow(() -> new InternalError());
  }

}
