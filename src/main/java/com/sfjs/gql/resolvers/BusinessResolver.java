package com.sfjs.gql.resolvers;

import java.net.URL;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.gql.svc.BusinessService;

import graphql.schema.DataFetchingEnvironment;

@RestController
@Transactional
public class BusinessResolver {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private BusinessService businessService;

  @QueryMapping(name = "business")
  public Optional<BusinessEntity> business(
      @Argument(name = "id") Long id,
      DataFetchingEnvironment environment) throws Exception {
    return businessService.getBusiness(id, environment);
  }

  @MutationMapping(name = "businessSignup")
  public Optional<BusinessEntity> businessSignup(
      @Argument(name = "email") String email,
      @Argument(name = "password") String password,
      @Argument(name = "name") String name,
      @Argument(name = "isBetaTester") Optional<Boolean> isBetaTester,
      @Argument(name = "contactName") String contactName,
      @Argument(name = "isEarlySignup") Optional<Boolean> isEarlySignup,
      @Argument(name = "referral") String referral) {
    return businessService.businessSignup(email, password, name, isBetaTester, contactName, isEarlySignup, referral);
  }

  @MutationMapping(name = "saveBusinessProfilePage1")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public boolean saveBusinessProfilePage1(
      @Argument(name = "smallBio") String smallBio,
      @Argument(name = "country") String country,
      @Argument(name = "location") String location,
      @Argument(name = "website") URL website,
      @Argument(name = "avatar") String avatar,
      DataFetchingEnvironment environment) throws Exception {
    return businessService.saveBusinessProfilePage1(smallBio, country, location, website, avatar, environment);
  }

  @MutationMapping(name = "saveBusinessProfilePage2")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public boolean saveBusinessProfilePage2(
      @Argument(name = "businessField") String businessField,
      @Argument(name = "missionVision") String missionVision,
      @Argument(name = "moreAboutBusiness") String moreAboutBusiness,
      DataFetchingEnvironment environment) throws Exception {
    return businessService.saveBusinessProfilePage2(businessField, missionVision, moreAboutBusiness, environment);
  }

}
