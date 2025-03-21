package com.sfjs.gql.resolvers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.data.core.Education;
import com.sfjs.data.core.Experience;
import com.sfjs.data.entity.FellowProfileEntity;
import com.sfjs.gql.svc.SignupService;

import graphql.schema.DataFetchingEnvironment;

@RestController
@EnableWebMvc
@Transactional
public class Signup {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private SignupService signupService;

  @MutationMapping(name = "signupBusiness")
  public Long signupBusiness(
      @Argument(name = "email") String email,
      @Argument(name = "password") String password,
      @Argument(name = "name") String name,
      @Argument(name = "isBetaTester") boolean isBetaTester,
      @Argument(name = "contactName") String contactName,
      @Argument(name = "isEarlySignup") boolean isEarlySignup,
      @Argument(name = "referral") String referral) {
    return signupService.signupBusiness(email, password, name, isBetaTester, contactName, isEarlySignup, referral);
  }

  @MutationMapping(name = "signupFellow")
  public Long signupFellow(
      @Argument(name="email") String email,
      @Argument(name="password") String password,
      @Argument(name="name") String name,
      @Argument(name="isBetaTester") boolean isBetaTester,
      @Argument(name="isCollaborator") boolean isCollaborator,
      @Argument(name="message") String message,
      @Argument(name="referralCode") String referralCode,
      @Argument(name="isReferralPartner") boolean isReferralPartner) {
    return signupService.signupFellow(email, password, name, isBetaTester, isCollaborator, message, referralCode, isReferralPartner);
  }

  @MutationMapping(name = "saveFellowProfilePage1")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage1(
      @Argument(name = "smallBio") String smallBio,
      @Argument(name = "country") String country,
      @Argument(name = "location") String location,
      @Argument(name = "skills") List<String> skills,
      @Argument(name = "jobTitles") List<String> jobTitles,
      @Argument(name = "languages") List<String> languages,
      DataFetchingEnvironment environment) throws Exception {
    return signupService.saveFellowProfilePage1(smallBio, country, location, skills, jobTitles, languages, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage2")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage2(
      @Argument(name = "experience") List<Experience> experience,
      @Argument(name = "education") List<Education> education,
      DataFetchingEnvironment environment) throws Exception {
    experience.stream().forEach(exp -> {
      logger.info("Experience: title - " + exp.getReference());
      logger.info("Experience: companyName - " + exp.getCompanyName());
      logger.info("Experience: yearDetails - " + exp.getYearDetails());
      logger.info("Experience: details - " + exp.getDetails());
    });
    return signupService.saveFellowProfilePage2(experience, education, environment);
  }

//  @MutationMapping(name = "saveProfile")
//  @PreAuthorize("hasRole('ROLE_FELLOW')")
//  public FellowProfileData saveProfile(@Argument(name = "requestBody") FellowProfileData requestBody,
//      DataFetchingEnvironment environment) throws Exception {
//    return signupService.saveProfile(requestBody, environment);
//  }

  @QueryMapping(name = "fellowProfile")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public FellowProfileEntity fellowProfile() {
    FellowProfileEntity profileEntity = signupService.getFellowProfile();
    return profileEntity;
  }

//  @MutationMapping(name = "saveBusinessProfile")
//  @PreAuthorize("hasRole('ROLE_BUSINESS')")
//  public BusinessProfile saveBusinessProfile(@Argument(name = "requestBody") BusinessProfile requestBody,
//      DataFetchingEnvironment environment) throws Exception {
//    return signupService.saveBusinessProfile(requestBody, environment);
//  }

}
