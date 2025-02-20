package com.sfjs.gql.svc;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sfjs.data.core.Education;
import com.sfjs.data.core.Experience;
import com.sfjs.data.core.Fellow;
import com.sfjs.data.entity.AccountEntity;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.EducationEntity;
import com.sfjs.data.entity.ExperienceEntity;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.data.entity.FellowProfileEntity;
import com.sfjs.data.entity.RoleEntity;
import com.sfjs.jpa.repo.AccountRepository;
import com.sfjs.jpa.repo.BusinessRepository;
import com.sfjs.jpa.repo.EducationRepository;
import com.sfjs.jpa.repo.ExperienceRepository;
import com.sfjs.jpa.repo.FellowRepository;
import com.sfjs.jpa.repo.ProfileRepository;
import com.sfjs.jpa.repo.RoleRepository;
import com.sfjs.security.AuthorizationService;

import graphql.schema.DataFetchingEnvironment;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SignupService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  FellowRepository fellowRepository;

  @Autowired
  BusinessRepository businessRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private AuthorizationService authorizationService;

  @Autowired
  private ExperienceRepository experienceRepository;

  @Autowired
  private EducationRepository educationRepository;

  @Autowired
  private ProfileRepository profileRepository;

//  @Autowired
//  private BusinessProfileRepository businessProfileRepository;

//  @Autowired
//  private AwardRepository awardRepository;

//  @Autowired
//  private ExperienceLevelRepository experienceLevelRepository;

//  @Autowired
//  private AccomplishmentRepository accomplishmentRepository;

//  @Autowired
//  private HobbyRepository hobbyRepository;

//  @Autowired
//  private BookOrQuoteRepository bookOrQuoteRepository;

//  @Autowired
//  private LinkRepository linkRepository;

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  static {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  private AccountEntity createNewAccount(String email, String password, String role) {
    logger.info("This email has never been used");
    AccountEntity newAccountEntity = new AccountEntity();
    newAccountEntity.setEmail(email);
    if (password != null) {
      newAccountEntity.setPassword(passwordEncoder.encode(password));
    }
    newAccountEntity.setEnabled(true);
    RoleEntity roleEntity = roleRepository.findByName(role);
    newAccountEntity.setRoles(Set.of(roleEntity));
    AccountEntity savedAccountEntity = accountRepository.save(newAccountEntity);
    return savedAccountEntity;
  }

  public Long signupFellow(String email, String password, String name,
      boolean isBetaTester, boolean isCollaborator, String message, String referralCode, boolean isReferralPartner) {
    logger.info(String.format("signupFellow: email [%s] password [%s]", email, password));
    AccountEntity existingAccountEntity = accountRepository.findByEmail(email);
    if (existingAccountEntity == null) {
      AccountEntity savedAccountEntity = createNewAccount(email, password, "FELLOW");
      FellowEntity newEntity = new FellowEntity();
      newEntity.setName(name);
      newEntity.setAccount(savedAccountEntity);
      setFellowFields(isBetaTester, isCollaborator, message, referralCode, isReferralPartner, newEntity);
      FellowEntity savedEntity = fellowRepository.save(newEntity);
      authorizationService.login(email, password);
      return savedEntity.getId();
    } else {
      FellowEntity existingEntity = existingAccountEntity.getFellow();
      if (existingEntity == null) {
        logger.info("No fellow associated with this account");
        // Every account must have exactly one fellow or one business
        throw new IllegalArgumentException("Email is unavailable");
      }
      logger.info("This account already exists, so make sure it's the same person");
      authorizationService.login(email, password);
      String existingEntityName = existingEntity.getName();
      if (existingEntityName != null && existingEntityName.contentEquals(name)) {
        logger.info("Same fellow");
        setFellowFields(isBetaTester, isCollaborator, message, referralCode, isReferralPartner, existingEntity);
        FellowEntity savedEntity = fellowRepository.save(existingEntity);
        return savedEntity.getId();
      } else {
        logger.info("Different fellow");
        throw new IllegalArgumentException("Email is unavailable");
      }
    }
  }

  private void setFellowFields(Boolean isBetaTester, boolean isCollaborator, String message, String referralCode,
      boolean isReferralPartner, Fellow fellow) {
    fellow.setBetaTester(isBetaTester);
    fellow.setCollaborator(isCollaborator);
    fellow.setMessage(message);
    fellow.setReferralCode(referralCode);
    fellow.setReferralPartner(isReferralPartner);
  }

//  private void login(String email, String password) throws Exception {
//    logger.info("This account already exists, so make sure it's the same person");
//    try {
//      authorizationService.login(email, password);
//    } catch (Exception ex) {
//      logger.log(Level.INFO, "login", ex);
////        throw new IllegalArgumentException("Email is unavailable");
//      throw ex;
//    }
//  }

  public Long signupBusiness(String email, String password, String name,
      Boolean isBetaTester, String contactName, Boolean isEarlySignup, String referral) {
    logger.info(String.format("signupBusiness: email [%s] password [%s]", email, password));
    AccountEntity existingAccountEntity = accountRepository.findByEmail(email);
    if (existingAccountEntity == null) {
      AccountEntity savedAccountEntity = createNewAccount(email, password, "BUSINESS");
      BusinessEntity newEntity = new BusinessEntity();
      newEntity.setName(name);
      newEntity.setAccount(savedAccountEntity);
      setBusinessFields(isBetaTester, contactName, isEarlySignup, referral, newEntity);
      BusinessEntity savedEntity = businessRepository.save(newEntity);
      authorizationService.login(email, password);
      return savedEntity.getId();
    } else {
      BusinessEntity existingEntity = existingAccountEntity.getBusiness();
      if (existingEntity == null) {
        logger.info("No business associated with this account");
        // Every account must have exactly one fellow or one business
        throw new IllegalArgumentException("Email is unavailable");
      }
      logger.info("This account already exists, so make sure it's the same person");
      authorizationService.login(email, password);
      String existingEntityName = existingEntity.getName();
      if (existingEntityName != null && existingEntityName.contentEquals(name)) {
        logger.info("Same business");
        setBusinessFields(isBetaTester, contactName, isEarlySignup, referral, existingEntity);
        BusinessEntity savedEntity = businessRepository.save(existingEntity);
        return savedEntity.getId();
      } else {
        logger.info("Different business");
        throw new IllegalArgumentException("Email is unavailable");
      }
    }
  }

  private void setBusinessFields(Boolean isBetaTester, String contactName, Boolean isEarlySignup, String referral,
      BusinessEntity newEntity) {
    newEntity.setBetaTester(isBetaTester);
    newEntity.setContactName(contactName);
    newEntity.setEarlySignup(isEarlySignup);
    newEntity.setReferral(referral);
  }

  public boolean saveFellowProfilePage1(String smallBio, String country, String location, List<String> skills,
      List<String> jobTitles, List<String> languages, DataFetchingEnvironment environment) {
    AccountEntity accountEntity = authorizationService.getAccount();
    FellowEntity fellowEntity = accountEntity.getFellow();
    FellowProfileEntity profileEntity = fellowEntity.getProfile();

    if (profileEntity == null) {
      profileEntity = new FellowProfileEntity();
      profileEntity.setFellow(fellowEntity);
    }

    profileEntity.setSmallBio(smallBio);
    profileEntity.setCountry(country);
    profileEntity.setLocation(location);
    profileEntity.setSkills(skills);
    profileEntity.setJobTitles(jobTitles);
//    profileEntity.setAvatar(avatar);
    profileEntity.setLanguages(languages);
    profileEntity = profileRepository.save(profileEntity);
    return true;
  }

  public boolean saveFellowProfilePage2(List<Experience> experience, List<Education> education,
      DataFetchingEnvironment environment) {
    AccountEntity accountEntity = authorizationService.getAccount();
    FellowEntity fellowEntity = accountEntity.getFellow();
    final FellowProfileEntity profileEntity = fellowEntity.getProfile();

    if (profileEntity == null) {
      logger.info("No profile associated with this fellow account");
      throw new IllegalArgumentException("No profile for this fellow account");
    }

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
  }

//  private BusinessEntity updateExistingBusiness(BusinessInput requestBody, BusinessEntity existingBusinessEntity) {
//    if (valueChanged(requestBody.getBetaTester(), existingBusinessEntity.getBetaTester())) {
//      existingBusinessEntity.setBetaTester(requestBody.getBetaTester());
//    }
//    if (valueChanged(requestBody.getContactName(), existingBusinessEntity.getContactName())) {
//      existingBusinessEntity.setContactName(requestBody.getContactName());
//    }
//    if (valueChanged(requestBody.getEarlySignup(), existingBusinessEntity.getEarlySignup())) {
//      existingBusinessEntity.setEarlySignup(requestBody.getEarlySignup());
//    }
//    if (valueChanged(requestBody.getReferral(), existingBusinessEntity.getReferral())) {
//      existingBusinessEntity.setReferral(requestBody.getReferral());
//    }
//    BusinessEntity savedBusinessEntity = businessRepository.save(existingBusinessEntity);
//    return savedBusinessEntity;
//  }

//  private BusinessEntity createNewBusiness(BusinessInput requestBody, AccountEntity existingAccountEntity) {
//    // Create a new BusinessEntity
//    // Associate new business with existing account
//    BusinessEntity newBusinessEntity = new BusinessEntity();
//    newBusinessEntity.setName(requestBody.getBusinessName());
//    newBusinessEntity.setAccount(existingAccountEntity);
////    existingAccountEntity.setBusiness(newBusinessEntity);
//    newBusinessEntity.setBetaTester(requestBody.getBetaTester() != null ? requestBody.getBetaTester() : false);
//    newBusinessEntity.setContactName(requestBody.getContactName());
//    newBusinessEntity.setEarlySignup(requestBody.getEarlySignup() != null ? requestBody.getEarlySignup() : false);
//    BusinessEntity savedBusinessEntity = businessRepository.save(newBusinessEntity);
//    return savedBusinessEntity;
//  }

//  private BusinessEntity createNewBusinessAndNewAccount(BusinessInput requestBody) {
//    // Create a new BusinessEntity
//    // Create a new AccountEntity
//    RoleEntity businessRoleEntity = roleRepository.findByName("BUSINESS");
//    AccountEntity newAccountEntity = new AccountEntity();
//    newAccountEntity.setEmail(requestBody.getEmail());
//    if (requestBody.getPassword() != null) {
//      newAccountEntity.setPassword(passwordEncoder.encode(requestBody.getPassword()));
//    }
//    newAccountEntity.setEnabled(true);
//    newAccountEntity.setRoles(Set.of(businessRoleEntity));
//    AccountEntity savedAccountEntity = accountRepository.save(newAccountEntity);
//    BusinessEntity newBusinessEntity = new BusinessEntity();
//    newBusinessEntity.setName(requestBody.getBusinessName());
//    newBusinessEntity.setAccount(savedAccountEntity);
//    newBusinessEntity.setBetaTester(requestBody.getBetaTester() != null ? requestBody.getBetaTester() : false);
//    newBusinessEntity.setContactName(requestBody.getContactName());
//    newBusinessEntity.setEarlySignup(requestBody.getEarlySignup() != null ? requestBody.getEarlySignup() : false);
//    BusinessEntity savedBusinessEntity = businessRepository.save(newBusinessEntity);
//    authorizationService.login(requestBody.getEmail(), requestBody.getPassword());
//    return savedBusinessEntity;
//  }

//  public Long saveProfile(FellowProfile requestBody, DataFetchingEnvironment environment) throws Exception {
//    AccountEntity accountEntity = authorizationService.getAccount();
//    FellowEntity fellowEntity = accountEntity.getFellow();
//    FellowProfileEntity profileEntity = fellowEntity.getProfile();
//
//    if (profileEntity == null) {
//      profileEntity = new FellowProfileEntity();
//      profileEntity.setFellow(fellowEntity);
//    }
//
//    convertBaseProfile(requestBody, profileEntity);
//    convertExtendedProfileData(requestBody, profileEntity);
//    profileEntity = profileRepository.save(profileEntity);
//    FellowProfileData extendedProfileData = new FellowProfileData();
//    String json = mapper.writeValueAsString(profileEntity);
//    logger.info("Extended profile entity: " + json);
//    convertBaseProfile(profileEntity, extendedProfileData);
//    convertExtendedProfileEntity(profileEntity, extendedProfileData);
//    json = mapper.writeValueAsString(extendedProfileData);
//    logger.info("Extended profile data: " + json);
//    return extendedProfileData;
//  }
//
//  private void convertExtendedProfileData(FellowProfileData in, FellowProfileEntity out) {
//    if (in.getExperience() != null) {
//      out.setExperience(in.getExperience().stream().map(data -> {
//        ExperienceEntity e = this.convertProfileElementData(data, ExperienceEntity.class, experienceRepository);
//        e.setProfile(out);
//        experienceRepository.save(e);
//        return e;
//      }).collect(Collectors.toList()));
//    }
//    if (in.getEducation() != null) {
//      out.setEducation(in.getEducation().stream().map(data -> {
//        EducationEntity e = this.convertProfileElementData(data, EducationEntity.class, educationRepository);
//        e.setProfile(out);
//        educationRepository.save(e);
//        return e;
//      }).collect(Collectors.toList()));
//    }
//    if (in.getAwards() != null) {
//      out.setAwards(in.getAwards().stream().map(data -> {
//        AwardEntity e = this.convertProfileElementData(data, AwardEntity.class, awardRepository);
//        e.setProfile(out);
//        awardRepository.save(e);
//        return e;
//      }).collect(Collectors.toList()));
//    }
//    if (in.getExperienceLevels() != null) {
//      out.setExperienceLevels(in.getExperienceLevels().stream().map(data -> {
//        ExperienceLevelEntity e = this.convertProfileElementData(data, ExperienceLevelEntity.class, experienceLevelRepository);
//        e.setProfile(out);
//        experienceLevelRepository.save(e);
//        return e;
//      }).collect(Collectors.toList()));
//    }
//    if (in.getAccomplishments() != null) {
//      out.setAccomplishments(in.getAccomplishments().stream().map(data -> {
//        AccomplishmentEntity e = this.convertProfileElementData(data, AccomplishmentEntity.class, accomplishmentRepository);
//        e.setProfile(out);
//        accomplishmentRepository.save(e);
//        return e;
//      }).collect(Collectors.toList()));
//    }
//    if (in.getHobbies() != null) {
//      out.setHobbies(in.getHobbies().stream().map(data -> {
//        HobbyEntity e = this.convertProfileElementData(data, HobbyEntity.class, hobbyRepository);
//        e.setProfile(out);
//        hobbyRepository.save(e);
//        return e;
//      }).collect(Collectors.toList()));
//    }
//    if (in.getBookOrQuote() != null) {
//      out.setBookOrQuote(in.getBookOrQuote().stream().map(data -> {
//        BookOrQuoteEntity e = this.convertProfileElementData(data, BookOrQuoteEntity.class, bookOrQuoteRepository);
//        e.setProfile(out);
//        bookOrQuoteRepository.save(e);
//        return e;
//      }).collect(Collectors.toList()));
//    }
//    if (in.getLinks() != null) {
//      out.setLinks(in.getLinks().stream().map(data -> {
//        LinkEntity e = this.convertProfileElementData(data, LinkEntity.class, linkRepository);
//        e.setProfile(out);
//        linkRepository.save(e);
//        return e;
//      }).collect(Collectors.toList()));
//    }
//  }
//
//  private <E extends BaseObject, D extends ProfileElementData>
//    E convertProfileElementData(D data, Class<E> entityType,
//      BaseRepository<E> repository) {
//    try {
//      String json = mapper.writeValueAsString(data);
//      E e = mapper.readValue(json, entityType);
////      e.setId(data.getObjectId());
//      if (e.getId() != null) {
//        Optional<E> opt = repository.findById(e.getId());
//        if (opt.isPresent()) {
//          return opt.get();
//        }
//      }
//      return repository.save(e);
//    } catch (Exception ex) {
//      return (E)null;
//    }
//  }
//
//  public FellowProfileData getFellowProfile() {
//    AccountEntity accountEntity = authorizationService.getAccount();
//    FellowEntity fellowEntity = accountEntity.getFellow();
//    FellowProfileEntity profileEntity = fellowEntity.getProfile();
//    FellowProfileData extendedProfileData = new FellowProfileData();
//    convertBaseProfile(profileEntity, extendedProfileData);
//    convertExtendedProfileEntity(profileEntity, extendedProfileData);
//    return extendedProfileData;
//  }
//
//  private void convertExtendedProfileEntity(FellowProfileEntity in, FellowProfileData out) {
//    if (in.getExperience() != null) {
//      out.setExperience(in.getExperience().stream().map(data -> {
//        return this.convertProfileElementEntity(data, Experience.class);
//      }).collect(Collectors.toList()));
//    }
//    if (in.getEducation() != null) {
//      out.setEducation(in.getEducation().stream().map(data -> {
//        return this.convertProfileElementEntity(data, Education.class);
//      }).collect(Collectors.toList()));
//    }
//    if (in.getAwards() != null) {
//      out.setAwards(in.getAwards().stream().map(data -> {
//        return this.convertProfileElementEntity(data, Award.class);
//      }).collect(Collectors.toList()));
//    }
//    if (in.getExperienceLevels() != null) {
//      out.setExperienceLevels(in.getExperienceLevels().stream().map(data -> {
//        return this.convertProfileElementEntity(data, ExperienceLevel.class);
//      }).collect(Collectors.toList()));
//    }
//    if (in.getAccomplishments() != null) {
//      out.setAccomplishments(in.getAccomplishments().stream().map(data -> {
//        return this.convertProfileElementEntity(data, Accomplishment.class);
//      }).collect(Collectors.toList()));
//    }
//    if (in.getHobbies() != null) {
//      out.setHobbies(in.getHobbies().stream().map(data -> {
//        return this.convertProfileElementEntity(data, Hobby.class);
//      }).collect(Collectors.toList()));
//    }
//    if (in.getBookOrQuote() != null) {
//      out.setBookOrQuote(in.getBookOrQuote().stream().map(data -> {
//        return this.convertProfileElementEntity(data, BookOrQuote.class);
//      }).collect(Collectors.toList()));
//    }
//    if (in.getLinks() != null) {
//      out.setLinks(in.getLinks().stream().map(data -> {
//        return this.convertProfileElementEntity(data, Link.class);
//      }).collect(Collectors.toList()));
//    }
//  }
//
//  private <E extends BaseObject, D extends ProfileElementData>
//    D convertProfileElementEntity(E entity,
//      Class<D> dataType) {
//    try {
//      String json = mapper.writeValueAsString(entity);
//      D d = mapper.readValue(json, dataType);
////      d.setObjectId(entity.getId());
//      return d;
//    } catch (Exception ex) {
//      return (D) null;
//    }
//  }
//
//  private void convertBaseProfile(FellowProfile in, FellowProfile out) {
////    out.setObjectId(in.getObjectId());
//    out.setSmallBio(in.getSmallBio());
//    out.setCountry(in.getCountry());
//    out.setLocation(in.getLocation());
//    out.setSkills(in.getSkills());
//    out.setJobTitles(in.getJobTitles());
//    out.setPassions(in.getPassions());
//    out.setLookingFor(in.getLookingFor());
//    out.setPetDetails(in.getPetDetails());
//    out.setAboutMe(in.getAboutMe());
//    out.setLocationOptions(in.getLocationOptions());
//    out.setLanguages(in.getLanguages());
//  }

//  public BusinessProfile saveBusinessProfile(BusinessProfile requestBody, DataFetchingEnvironment environment) throws Exception {
//    AccountEntity accountEntity = authorizationService.getAccount();
//    BusinessEntity businessEntity = accountEntity.getBusiness();
//    BusinessProfileEntity profileEntity = businessEntity.getBusinessProfile();
//
//    if (profileEntity == null) {
//      profileEntity = new BusinessProfileEntity();
//      profileEntity.setBusiness(businessEntity);
//    }
//
//    convertBusinessProfile(requestBody, profileEntity);
//    profileEntity = businessProfileRepository.save(profileEntity);
//    BusinessProfile businessProfileData = new BusinessProfile();
//    String json = mapper.writeValueAsString(profileEntity);
//    logger.info("Business profile entity: " + json);
//    convertBusinessProfile(profileEntity, businessProfileData);
//    json = mapper.writeValueAsString(businessProfileData);
//    logger.info("Business profile data: " + json);
//    return businessProfileData;
//  }
//
//  private void convertBusinessProfile(BusinessProfile in, BusinessProfile out) {
////    out.setObjectId(in.getObjectId());
//    out.setSmallBio(in.getSmallBio());
//    out.setCountry(in.getCountry());
//    out.setLocation(in.getLocation());
//    out.setWebsite(in.getWebsite());
//    out.setBusinessField(in.getBusinessField());
//    out.setMissionVision(in.getMissionVision());
//    out.setMoreAboutBusiness(in.getMoreAboutBusiness());
//    out.setBillingDetails(in.getBillingDetails());
//    out.setAmountDue(in.getAmountDue());
//  }

}
