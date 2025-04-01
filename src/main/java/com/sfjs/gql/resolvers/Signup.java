package com.sfjs.gql.resolvers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.sfjs.data.core.Accomplishment;
import com.sfjs.data.core.Award;
import com.sfjs.data.core.BookOrQuote;
import com.sfjs.data.core.Education;
import com.sfjs.data.core.Experience;
import com.sfjs.data.core.ExperienceLevel;
import com.sfjs.data.core.Hobby;
import com.sfjs.data.core.Link;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.BusinessProfileEntity;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.data.entity.FellowProfileEntity;
import com.sfjs.gql.svc.SignupService;

import graphql.schema.DataFetchingEnvironment;

@RestController
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
      @Argument(name = "avatar") String avatar,
      @Argument(name = "languages") List<String> languages,
      DataFetchingEnvironment environment) throws Exception {
    return signupService.saveFellowProfilePage1(smallBio, country, location, skills, jobTitles, avatar, languages, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage2")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage2(
      @Argument(name = "experience") List<Experience> experience,
      @Argument(name = "education") List<Education> education,
      DataFetchingEnvironment environment) throws Exception {
    return signupService.saveFellowProfilePage2(experience, education, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage3")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage3(
      @Argument(name = "awards") List<Award> awards,
      @Argument(name = "experienceLevels") List<ExperienceLevel> experienceLevels,
      @Argument(name = "accomplishments") List<Accomplishment> accomplishments,
      DataFetchingEnvironment environment) throws Exception {
    return signupService.saveFellowProfilePage3(awards, experienceLevels, accomplishments, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage4")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage4(
    @Argument(name = "passions") String passions,
    @Argument(name = "lookingFor") String lookingFor,
    @Argument(name = "locationOptions") List<String> locationOptions,
    DataFetchingEnvironment environment) throws Exception {
    return signupService.saveFellowProfilePage4(passions, lookingFor, locationOptions, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage5")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage5(
    @Argument(name = "hobbies") List<Hobby> hobbies,
    @Argument(name = "bookOrQuote") List<BookOrQuote> bookOrQuote,
    @Argument(name = "petDetails") String petDetails,
    DataFetchingEnvironment environment) throws Exception {
    return signupService.saveFellowProfilePage5(hobbies, bookOrQuote, petDetails, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage6")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage6(
    @Argument(name = "links") List<Link> links,
    @Argument(name = "aboutMe") String aboutMe,
    DataFetchingEnvironment environment) throws Exception {
    return signupService.saveFellowProfilePage6(links, aboutMe, environment);
  }

//  @MutationMapping(name = "saveProfile")
//  @PreAuthorize("hasRole('ROLE_FELLOW')")
//  public FellowProfileData saveProfile(@Argument(name = "requestBody") FellowProfileData requestBody,
//      DataFetchingEnvironment environment) throws Exception {
//    return signupService.saveProfile(requestBody, environment);
//  }

  @QueryMapping(name = "fellowProfile")
  public Optional<FellowProfileEntity> fellowProfile(
      @Argument(name = "id") Long id
      ) {
    return signupService.getFellowProfile(id);
  }

  @QueryMapping(name = "fellow")
  public Optional<FellowEntity> fellow(
      @Argument(name = "id") Long id,
      DataFetchingEnvironment environment) throws Exception {
    return signupService.getFellow(id, environment);
  }

  @QueryMapping(name = "business")
  public Optional<BusinessEntity> business(
      @Argument(name = "id") Long id,
      DataFetchingEnvironment environment) throws Exception {
    return signupService.getBusiness(id, environment);
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
    return signupService.saveBusinessProfilePage1(smallBio, country, location, website, avatar, environment);
  }

  @MutationMapping(name = "saveBusinessProfilePage2")
  @PreAuthorize("hasRole('ROLE_BUSINESS')")
  public boolean saveBusinessProfilePage2(
      @Argument(name = "businessField") String businessField,
      @Argument(name = "missionVision") String missionVision,
      @Argument(name = "moreAboutBusiness") String moreAboutBusiness,
      DataFetchingEnvironment environment) throws Exception {
    return signupService.saveBusinessProfilePage2(businessField, missionVision, moreAboutBusiness, environment);
  }

  @QueryMapping(name = "businessProfile")
  public Optional<BusinessProfileEntity> businessProfile(
      @Argument(name = "id") Long id
      ) {
    return signupService.getBusinessProfile(id);
  }


}
