package com.sfjs.gql.svc;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.core.Accomplishment;
import com.sfjs.data.core.Award;
import com.sfjs.data.core.BookOrQuote;
import com.sfjs.data.core.Education;
import com.sfjs.data.core.Experience;
import com.sfjs.data.core.ExperienceLevel;
import com.sfjs.data.core.FellowProfile.ProfileSteps;
import com.sfjs.data.core.Hobby;
import com.sfjs.data.core.Link;
import com.sfjs.data.entity.AccomplishmentEntity;
import com.sfjs.data.entity.AwardEntity;
import com.sfjs.data.entity.BookOrQuoteEntity;
import com.sfjs.data.entity.EducationEntity;
import com.sfjs.data.entity.ExperienceEntity;
import com.sfjs.data.entity.ExperienceLevelEntity;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.data.entity.FellowProfileEntity;
import com.sfjs.data.entity.HobbyEntity;
import com.sfjs.data.entity.LinkEntity;
import com.sfjs.jpa.repo.AccomplishmentRepository;
import com.sfjs.jpa.repo.AccountRepository;
import com.sfjs.jpa.repo.AwardRepository;
import com.sfjs.jpa.repo.BookOrQuoteRepository;
import com.sfjs.jpa.repo.EducationRepository;
import com.sfjs.jpa.repo.ExperienceLevelRepository;
import com.sfjs.jpa.repo.ExperienceRepository;
import com.sfjs.jpa.repo.FellowRepository;
import com.sfjs.jpa.repo.HobbyRepository;
import com.sfjs.jpa.repo.LinkRepository;
import com.sfjs.jpa.repo.ProfileRepository;
import com.sfjs.security.AuthorizationService;

import graphql.schema.DataFetchingEnvironment;

@Service
@Transactional
public class FellowService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private AccountService accountService;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private AuthorizationService authorizationService;

  @Autowired
  private FellowRepository fellowRepository;

  @Autowired
  private ExperienceRepository experienceRepository;

  @Autowired
  private EducationRepository educationRepository;

  @Autowired
  private AwardRepository awardRepository;

  @Autowired
  private ExperienceLevelRepository experienceLevelRepository;

  @Autowired
  private AccomplishmentRepository accomplishmentRepository;

  @Autowired
  private HobbyRepository hobbyRepository;

  @Autowired
  private BookOrQuoteRepository bookOrQuoteRepository;

  @Autowired
  private LinkRepository linkRepository;

  @Autowired
  private ProfileRepository profileRepository;

  public Optional<FellowEntity> fellowSignup(
      @Argument(name="email") String email,
      @Argument(name="password") String password,
      @Argument(name="name") String name,
      @Argument(name="isBetaTester") Optional<Boolean> isBetaTester,
      @Argument(name="isCollaborator") Optional<Boolean> isCollaborator,
      @Argument(name="message") String message,
      @Argument(name="referralCode") String referralCode,
      @Argument(name="isReferralPartner") Optional<Boolean> isReferralPartner) {
    logger.info(String.format("fellowSignup: email [%s] name [%s]", email, name));
    return accountRepository.findByEmail(email).map(accountEntity -> {
      return Optional.of(accountEntity.getFellow()).map(existingFellow -> {
        logger.info("This account already exists, so make sure it's the same person");
        authorizationService.login(email, password);
        setFellowFields(existingFellow, name, isBetaTester, isCollaborator, message, referralCode, isReferralPartner);
        return fellowRepository.save(existingFellow);
      }).orElseThrow(() -> {
        logger.info("No fellow associated with this account");
        throw new IllegalArgumentException("Email is unavailable");
      });
    }).or(() -> {
      return accountService.createNewFellowAccount(email, password).map(account -> {
        authorizationService.login(email, password);
        FellowEntity newEntity = new FellowEntity();
        newEntity.setAccount(account);
        setFellowFields(newEntity, name, isBetaTester, isCollaborator, message, referralCode, isReferralPartner);
        return fellowRepository.save(newEntity);
      });
    });
  }

  private void setFellowFields(FellowEntity existingFellow,
      @Argument(name="name") String name,
      @Argument(name="isBetaTester") Optional<Boolean> isBetaTester,
      @Argument(name="isCollaborator") Optional<Boolean> isCollaborator,
      @Argument(name="message") String message,
      @Argument(name="referralCode") String referralCode,
      @Argument(name="isReferralPartner") Optional<Boolean> isReferralPartner) {
    existingFellow.setName(name);
    isBetaTester.ifPresent(value -> existingFellow.setBetaTester(value));
    isCollaborator.ifPresent(value -> existingFellow.setCollaborator(value));
    existingFellow.setMessage(message);
    existingFellow.setReferralCode(referralCode);
    isReferralPartner.ifPresent(value -> existingFellow.setReferralPartner(value));
  }

  public boolean saveFellowProfilePage1(String smallBio, String country, String location, List<String> skills,
      List<String> jobTitles, String avatar, List<String> languages, DataFetchingEnvironment environment) {
    return authorizationService.getAccount().map(accountEntity -> {
    FellowEntity fellowEntity = accountEntity.getFellow();
    FellowProfileEntity profileEntity = fellowEntity.getProfile();

    if (profileEntity == null) {
      profileEntity = new FellowProfileEntity();
      profileEntity.setFellow(fellowEntity);
    }

    profileEntity.getStepsCompleted().add(ProfileSteps.STEP_ONE.getValue());

    profileEntity.setSmallBio(smallBio);
    profileEntity.setCountry(country);
    profileEntity.setLocation(location);
    profileEntity.setSkills(skills);
    profileEntity.setJobTitles(jobTitles);
    profileEntity.setAvatar(avatar);
    profileEntity.setLanguages(languages);
    profileEntity = profileRepository.save(profileEntity);
    return true;
    }).orElseThrow(() -> new InternalError());
  }

  public boolean saveFellowProfilePage2(List<Experience> experience, List<Education> education,
      DataFetchingEnvironment environment) {
    return authorizationService.getAccount().map(accountEntity -> {
    FellowEntity fellowEntity = accountEntity.getFellow();
    final FellowProfileEntity profileEntity = fellowEntity.getProfile();

    if (profileEntity == null) {
      logger.info("No profile associated with this fellow account");
      throw new IllegalArgumentException("No profile for this fellow account");
    }

    profileEntity.getStepsCompleted().add(ProfileSteps.STEP_TWO.getValue());

    profileEntity.setExperience(experience.stream().map(data -> {
      ExperienceEntity entity = new ExperienceEntity();
      entity.setCompanyName(data.getCompanyName());
      entity.setTitle(data.getTitle());
      entity.setYearDetails(data.getYearDetails());
      entity.setDetails(data.getDetails());
      entity.setProfile(profileEntity);
      entity = experienceRepository.save(entity);
      return entity;
    }).collect(Collectors.toList()));

    profileEntity.setEducation(education.stream().map(data -> {
      EducationEntity entity = new EducationEntity();
      entity.setDegree(data.getDegree());
      entity.setSchool(data.getSchool());
      entity.setFieldOfStudy(data.getFieldOfStudy());
      entity.setProfile(profileEntity);
      entity = educationRepository.save(entity);
      return entity;
    }).collect(Collectors.toList()));

    profileRepository.save(profileEntity);
    return true;
    }).orElseThrow(() -> new InternalError());
  }

  public boolean saveFellowProfilePage3(List<Award> awards, List<ExperienceLevel> experienceLevels,
      List<Accomplishment> accomplishments, DataFetchingEnvironment environment) {
    return authorizationService.getAccount().map(accountEntity -> {
    FellowEntity fellowEntity = accountEntity.getFellow();
    final FellowProfileEntity profileEntity = fellowEntity.getProfile();

    if (profileEntity == null) {
      logger.info("No profile associated with this fellow account");
      throw new IllegalArgumentException("No profile for this fellow account");
    }

    profileEntity.getStepsCompleted().add(ProfileSteps.STEP_THREE.getValue());

    profileEntity.setAwards(awards.stream().map(data -> {
      AwardEntity entity = new AwardEntity();
      entity.setAwardTitle(data.getAwardTitle());
      entity.setGivenBy(data.getGivenBy());
      entity.setAwardDetails(data.getAwardDetails());
      entity.setProfile(profileEntity);
      entity = awardRepository.save(entity);
      return entity;
    }).collect(Collectors.toList()));

    profileEntity.setExperienceLevels(experienceLevels.stream().map(data -> {
      ExperienceLevelEntity entity = new ExperienceLevelEntity();
      entity.setExperienceLevel(data.getExperienceLevel());
      entity.setExpLevelSkill(data.getExpLevelSkill());
      entity.setSkillYears(data.getSkillYears());
      entity.setProfile(profileEntity);
      entity = experienceLevelRepository.save(entity);
      return entity;
    }).collect(Collectors.toList()));

    profileEntity.setAccomplishments(accomplishments.stream().map(data -> {
      AccomplishmentEntity entity = new AccomplishmentEntity();
      entity.setAccTitle(data.getAccTitle());
      entity.setAccDetails(data.getAccDetails());
      entity.setProfile(profileEntity);
      entity = accomplishmentRepository.save(entity);
      return entity;
    }).collect(Collectors.toList()));

    profileRepository.save(profileEntity);
    return true;
    }).orElseThrow(() -> new InternalError());
  }

  public boolean saveFellowProfilePage4(String passions, String lookingFor, List<String> locationOptions,
      DataFetchingEnvironment environment) {
    return authorizationService.getAccount().map(accountEntity -> {
    FellowEntity fellowEntity = accountEntity.getFellow();
    FellowProfileEntity profileEntity = fellowEntity.getProfile();

    if (profileEntity == null) {
      profileEntity = new FellowProfileEntity();
      profileEntity.setFellow(fellowEntity);
    }

    profileEntity.getStepsCompleted().add(ProfileSteps.STEP_FOUR.getValue());

    profileEntity.setPassions(passions);
    profileEntity.setLookingFor(lookingFor);
    profileEntity.setLocationOptions(locationOptions);
    profileEntity = profileRepository.save(profileEntity);
    return true;
    }).orElseThrow(() -> new InternalError());
  }

  public boolean saveFellowProfilePage5(List<Hobby> hobbies, List<BookOrQuote> bookOrQuote, String petDetails,
      DataFetchingEnvironment environment) {
    return authorizationService.getAccount().map(accountEntity -> {
    FellowEntity fellowEntity = accountEntity.getFellow();
    final FellowProfileEntity profileEntity = fellowEntity.getProfile();

    if (profileEntity == null) {
      logger.info("No profile associated with this fellow account");
      throw new IllegalArgumentException("No profile for this fellow account");
    }

    profileEntity.getStepsCompleted().add(ProfileSteps.STEP_FIVE.getValue());

    profileEntity.setHobbies(hobbies.stream().map( data -> {
      HobbyEntity entity = new HobbyEntity();
      entity.setHobbyTitle(data.getHobbyTitle());
      entity.setHowLong(data.getHowLong());
      entity.setProfile(profileEntity);
      entity = hobbyRepository.save(entity);
      return entity;
    }).collect(Collectors.toList()));

    profileEntity.setBookOrQuote(bookOrQuote.stream().map( data -> {
      BookOrQuoteEntity entity = new BookOrQuoteEntity();
      entity.setBookOrQuote(data.getBookOrQuote());
      entity.setAuthor(data.getAuthor());
      entity.setProfile(profileEntity);
      entity = bookOrQuoteRepository.save(entity);
      return entity;
    }).collect(Collectors.toList()));

    profileEntity.setPetDetails(petDetails);
    profileRepository.save(profileEntity);
    return true;
    }).orElseThrow(() -> new InternalError());
  }

  public boolean saveFellowProfilePage6(List<Link> links, String aboutMe, DataFetchingEnvironment environment) {
    return authorizationService.getAccount().map(accountEntity -> {
    FellowEntity fellowEntity = accountEntity.getFellow();
    final FellowProfileEntity profileEntity = fellowEntity.getProfile();

    if (profileEntity == null) {
      logger.info("No profile associated with this fellow account");
      throw new IllegalArgumentException("No profile for this fellow account");
    }

    profileEntity.getStepsCompleted().add(ProfileSteps.STEP_SIX.getValue());

    profileEntity.setLinks(links.stream().map( data -> {
      LinkEntity entity = new LinkEntity();
      entity.setLinkType(data.getLinkType());
      entity.setLink(data.getLink());
      entity.setProfile(profileEntity);
      entity = linkRepository.save(entity);
      return entity;
    }).collect(Collectors.toList()));

    profileEntity.setAboutMe(aboutMe);
    profileRepository.save(profileEntity);
    return true;
    }).orElseThrow(() -> new InternalError());
  }

  public Optional<FellowEntity> getFellow(Long id, DataFetchingEnvironment environment) {
    return fellowRepository.findById(id);
  }
}
