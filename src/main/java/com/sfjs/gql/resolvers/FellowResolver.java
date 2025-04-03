package com.sfjs.gql.resolvers;

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
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.gql.svc.FellowService;

import graphql.schema.DataFetchingEnvironment;

@RestController
@Transactional
public class FellowResolver {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private FellowService fellowService;

  @QueryMapping(name = "fellow")
  public Optional<FellowEntity> fellow(
      @Argument(name = "id") Long id,
      DataFetchingEnvironment environment) throws Exception {
    return fellowService.getFellow(id, environment);
  }

  @MutationMapping(name = "fellowSignup")
  public Optional<FellowEntity> fellowSignup(
      @Argument(name="email") String email,
      @Argument(name="password") String password,
      @Argument(name="name") String name,
      @Argument(name="isBetaTester") Optional<Boolean> isBetaTester,
      @Argument(name="isCollaborator") Optional<Boolean> isCollaborator,
      @Argument(name="message") String message,
      @Argument(name="referralCode") String referralCode,
      @Argument(name="isReferralPartner") Optional<Boolean> isReferralPartner) {
    return fellowService.fellowSignup(email, password, name, isBetaTester, isCollaborator, message, referralCode, isReferralPartner);
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
    return fellowService.saveFellowProfilePage1(smallBio, country, location, skills, jobTitles, avatar, languages, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage2")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage2(
      @Argument(name = "experience") List<Experience> experience,
      @Argument(name = "education") List<Education> education,
      DataFetchingEnvironment environment) throws Exception {
    return fellowService.saveFellowProfilePage2(experience, education, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage3")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage3(
      @Argument(name = "awards") List<Award> awards,
      @Argument(name = "experienceLevels") List<ExperienceLevel> experienceLevels,
      @Argument(name = "accomplishments") List<Accomplishment> accomplishments,
      DataFetchingEnvironment environment) throws Exception {
    return fellowService.saveFellowProfilePage3(awards, experienceLevels, accomplishments, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage4")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage4(
    @Argument(name = "passions") String passions,
    @Argument(name = "lookingFor") String lookingFor,
    @Argument(name = "locationOptions") List<String> locationOptions,
    DataFetchingEnvironment environment) throws Exception {
    return fellowService.saveFellowProfilePage4(passions, lookingFor, locationOptions, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage5")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage5(
    @Argument(name = "hobbies") List<Hobby> hobbies,
    @Argument(name = "bookOrQuote") List<BookOrQuote> bookOrQuote,
    @Argument(name = "petDetails") String petDetails,
    DataFetchingEnvironment environment) throws Exception {
    return fellowService.saveFellowProfilePage5(hobbies, bookOrQuote, petDetails, environment);
  }

  @MutationMapping(name = "saveFellowProfilePage6")
  @PreAuthorize("hasRole('ROLE_FELLOW')")
  public boolean saveFellowProfilePage6(
    @Argument(name = "links") List<Link> links,
    @Argument(name = "aboutMe") String aboutMe,
    DataFetchingEnvironment environment) throws Exception {
    return fellowService.saveFellowProfilePage6(links, aboutMe, environment);
  }

}
