package com.sfjs.gql.svc;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sfjs.data.entity.AccomplishmentEntity;
import com.sfjs.data.entity.AccountEntity;
import com.sfjs.data.entity.AwardEntity;
import com.sfjs.data.entity.BookOrQuoteEntity;
import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.BusinessProfileEntity;
import com.sfjs.data.entity.EducationEntity;
import com.sfjs.data.entity.ExperienceEntity;
import com.sfjs.data.entity.ExperienceLevelEntity;
import com.sfjs.data.entity.FellowProfileEntity;
import com.sfjs.data.entity.FellowEntity;
import com.sfjs.data.entity.HobbyEntity;
import com.sfjs.data.entity.LinkEntity;
import com.sfjs.data.entity.RoleEntity;
import com.sfjs.jpa.repo.AccomplishmentRepository;
import com.sfjs.jpa.repo.AccountRepository;
import com.sfjs.jpa.repo.AwardRepository;
import com.sfjs.jpa.repo.BaseRepository;
import com.sfjs.jpa.repo.BookOrQuoteRepository;
import com.sfjs.jpa.repo.BusinessProfileRepository;
import com.sfjs.jpa.repo.BusinessRepository;
import com.sfjs.jpa.repo.EducationRepository;
import com.sfjs.jpa.repo.ExperienceLevelRepository;
import com.sfjs.jpa.repo.ExperienceRepository;
import com.sfjs.jpa.repo.FellowRepository;
import com.sfjs.jpa.repo.HobbyRepository;
import com.sfjs.jpa.repo.LinkRepository;
import com.sfjs.jpa.repo.ProfileRepository;
import com.sfjs.jpa.repo.RoleRepository;
import com.sfjs.data.BaseObject;
import com.sfjs.data.api.FellowProfileData;
import com.sfjs.data.api.ProfileElementData;
import com.sfjs.data.core.Accomplishment;
import com.sfjs.data.core.Award;
import com.sfjs.data.core.BookOrQuote;
import com.sfjs.data.core.BusinessProfile;
import com.sfjs.data.core.Education;
import com.sfjs.data.core.Experience;
import com.sfjs.data.core.ExperienceLevel;
import com.sfjs.data.core.FellowProfile;
import com.sfjs.data.core.Hobby;
import com.sfjs.data.core.Link;
import com.sfjs.gql.schema.BusinessInput;
import com.sfjs.gql.schema.FellowInput;
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

  @Autowired
  private BusinessProfileRepository businessProfileRepository;

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

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  static {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
  }

  public Long signupFellow(FellowInput requestBody) {
    logger.info("signupFellow: " + requestBody);
    String email = requestBody.getEmail();
    AccountEntity existingAccountEntity = accountRepository.findByEmail(email);
    if (existingAccountEntity == null) {
      logger.info("This email has never been used");
      FellowEntity savedFellowEntity = createNewFellowAndNewAccount(requestBody);
      return savedFellowEntity.getId();
    } else {
      logger.info("This account already exists, so make sure it's the same person");
      try {
        authorizationService.login(requestBody.getEmail(), requestBody.getPassword());
      } catch (Exception ex) {
        logger.log(Level.INFO, "login", ex);
//        throw new IllegalArgumentException("Email is unavailable");
        throw ex;
      }
      FellowEntity existingFellowEntity = existingAccountEntity.getFellow();
      if (existingFellowEntity == null) {
        logger.info("No fellow");
        FellowEntity savedFellowEntity = createNewFellow(requestBody, existingAccountEntity);
        return savedFellowEntity.getId();
      } else {
        String existingFellowName = existingFellowEntity.getName();
        if (existingFellowName != null && existingFellowName.contentEquals(requestBody.getName())) {
          logger.info("Same fellow");
          FellowEntity savedFellowEntity = updateExistingFellow(requestBody, existingFellowEntity);
          return savedFellowEntity.getId();
        } else {
          logger.info("Different fellow");
          throw new IllegalArgumentException("Email is unavailable");
        }
      }
    }
  }

  private FellowEntity updateExistingFellow(FellowInput requestBody, FellowEntity existingFellowEntity) {
    if (valueChanged(requestBody.getBetaTester(), existingFellowEntity.getBetaTester())) {
      existingFellowEntity.setBetaTester(requestBody.getBetaTester());
    }
    if (valueChanged(requestBody.getCollaborator(), existingFellowEntity.isCollaborator())) {
      existingFellowEntity.setCollaborator(requestBody.getCollaborator());
    }
    if (valueChanged(requestBody.getMessage(), existingFellowEntity.getMessage())) {
      existingFellowEntity.setMessage(requestBody.getMessage());
    }
    if (valueChanged(requestBody.getReferralCode(), existingFellowEntity.getReferralCode())) {
      existingFellowEntity.setReferralCode(requestBody.getReferralCode());
    }
    if (valueChanged(requestBody.getReferralPartner(), existingFellowEntity.isReferralPartner())) {
      existingFellowEntity.setReferralPartner(requestBody.getReferralPartner());
    }
    FellowEntity savedFellowEntity = fellowRepository.save(existingFellowEntity);
    return savedFellowEntity;
  }

  private boolean valueChanged(String newValue, String original) {
    if (newValue == null)
      return false;
    if (original == null)
      return true;
    return !newValue.contentEquals(original);
  }

  private boolean valueChanged(Boolean newValue, Boolean original) {
    if (newValue == null)
      return false;
    if (original == null)
      return true;
    return newValue.booleanValue() != original.booleanValue();
  }

  /**
   * Create a new fellow entity
   *
   * Because the is a new entity, we don't need to worry about changing value of
   * existing fields. If an input field is null Then it's okay to set the entity
   * field to null
   *
   * @param requestBody
   * @param existingAccountEntity
   * @return
   */
  private FellowEntity createNewFellow(FellowInput requestBody, AccountEntity existingAccountEntity) {
    // Create a new FellowEntity
    // Associate new fellow with existing account
    FellowEntity newFellowEntity = new FellowEntity();
    newFellowEntity.setName(requestBody.getName());
    newFellowEntity.setAccount(existingAccountEntity);
    // TODO is this necessary?
    existingAccountEntity.setFellow(newFellowEntity);
    newFellowEntity.setBetaTester(requestBody.getBetaTester());
    newFellowEntity.setCollaborator(requestBody.getCollaborator());
    newFellowEntity.setMessage(requestBody.getMessage());
    newFellowEntity.setReferralCode(requestBody.getReferralCode());
    newFellowEntity.setReferralPartner(requestBody.getReferralPartner());
    FellowEntity savedFellowEntity = fellowRepository.save(newFellowEntity);
    return savedFellowEntity;
  }

  /**
   * Create a new fellow entity and account entity
   *
   * Because the is a new entity, we don't need to worry about changing value of
   * existing fields. If an input field is null Then it's okay to set the entity
   * field to null
   *
   * @param requestBody
   * @return
   */
  private FellowEntity createNewFellowAndNewAccount(FellowInput requestBody) {
    // Create a new FellowEntity
    // Create a new AccountEntity
    RoleEntity fellowRoleEntity = roleRepository.findByName("FELLOW");
    AccountEntity newAccountEntity = new AccountEntity();
    newAccountEntity.setEmail(requestBody.getEmail());
    if (requestBody.getPassword() != null) {
      newAccountEntity.setPassword(passwordEncoder.encode(requestBody.getPassword()));
    }
    newAccountEntity.setEnabled(true);
    newAccountEntity.setRoles(Set.of(fellowRoleEntity));
    AccountEntity savedAccountEntity = accountRepository.save(newAccountEntity);
    FellowEntity newFellowEntity = new FellowEntity();
    newFellowEntity.setName(requestBody.getName());
    newFellowEntity.setAccount(savedAccountEntity);
    newFellowEntity.setBetaTester(requestBody.getBetaTester() != null ? requestBody.getBetaTester() : false);
    newFellowEntity.setCollaborator(requestBody.getCollaborator() != null ? requestBody.getCollaborator() : false);
    newFellowEntity.setMessage(requestBody.getMessage());
    newFellowEntity.setReferralCode(requestBody.getReferralCode());
    newFellowEntity
        .setReferralPartner(requestBody.getReferralPartner() != null ? requestBody.getReferralPartner() : false);
    FellowEntity savedFellowEntity = fellowRepository.save(newFellowEntity);
    authorizationService.login(requestBody.getEmail(), requestBody.getPassword());
    return savedFellowEntity;
  }

  public Long signupBusiness(BusinessInput requestBody) {
    String email = requestBody.getEmail();
    AccountEntity existingAccountEntity = accountRepository.findByEmail(email);
    if (existingAccountEntity == null) {
      // This email has never been used
      BusinessEntity savedBusinessEntity = createNewBusinessAndNewAccount(requestBody);
      return savedBusinessEntity.getId();
    } else {
      // This account already exists, so make sure it's the same person
      try {
        authorizationService.login(requestBody.getEmail(), requestBody.getPassword());
      } catch (Exception ex) {
        logger.log(Level.INFO, "login", ex);
//        throw new IllegalArgumentException("Email is unavailable");
        throw ex;
      }
      BusinessEntity existingBusinessEntity = existingAccountEntity.getBusiness();
      if (existingBusinessEntity == null) {
        // No business
        BusinessEntity savedBusinessEntity = createNewBusiness(requestBody, existingAccountEntity);
        return savedBusinessEntity.getId();
      } else {
        String existingBusinessName = existingBusinessEntity.getName();
        if (existingBusinessName != null && existingBusinessName.contentEquals(requestBody.getBusinessName())) {
          // Same business
          BusinessEntity savedBusinessEntity = updateExistingBusiness(requestBody, existingBusinessEntity);
          return savedBusinessEntity.getId();
        } else {
          // Different business
          throw new IllegalArgumentException("Email is unavailable");
        }
      }
    }
  }

  private BusinessEntity updateExistingBusiness(BusinessInput requestBody, BusinessEntity existingBusinessEntity) {
    if (valueChanged(requestBody.getBetaTester(), existingBusinessEntity.getBetaTester())) {
      existingBusinessEntity.setBetaTester(requestBody.getBetaTester());
    }
    if (valueChanged(requestBody.getContactName(), existingBusinessEntity.getContactName())) {
      existingBusinessEntity.setContactName(requestBody.getContactName());
    }
    if (valueChanged(requestBody.getEarlySignup(), existingBusinessEntity.getEarlySignup())) {
      existingBusinessEntity.setEarlySignup(requestBody.getEarlySignup());
    }
    if (valueChanged(requestBody.getReferral(), existingBusinessEntity.getReferral())) {
      existingBusinessEntity.setReferral(requestBody.getReferral());
    }
    BusinessEntity savedBusinessEntity = businessRepository.save(existingBusinessEntity);
    return savedBusinessEntity;
  }

  private BusinessEntity createNewBusiness(BusinessInput requestBody, AccountEntity existingAccountEntity) {
    // Create a new BusinessEntity
    // Associate new business with existing account
    BusinessEntity newBusinessEntity = new BusinessEntity();
    newBusinessEntity.setName(requestBody.getBusinessName());
    newBusinessEntity.setAccount(existingAccountEntity);
//    existingAccountEntity.setBusiness(newBusinessEntity);
    newBusinessEntity.setBetaTester(requestBody.getBetaTester() != null ? requestBody.getBetaTester() : false);
    newBusinessEntity.setContactName(requestBody.getContactName());
    newBusinessEntity.setEarlySignup(requestBody.getEarlySignup() != null ? requestBody.getEarlySignup() : false);
    BusinessEntity savedBusinessEntity = businessRepository.save(newBusinessEntity);
    return savedBusinessEntity;
  }

  private BusinessEntity createNewBusinessAndNewAccount(BusinessInput requestBody) {
    // Create a new BusinessEntity
    // Create a new AccountEntity
    RoleEntity businessRoleEntity = roleRepository.findByName("BUSINESS");
    AccountEntity newAccountEntity = new AccountEntity();
    newAccountEntity.setEmail(requestBody.getEmail());
    if (requestBody.getPassword() != null) {
      newAccountEntity.setPassword(passwordEncoder.encode(requestBody.getPassword()));
    }
    newAccountEntity.setEnabled(true);
    newAccountEntity.setRoles(Set.of(businessRoleEntity));
    AccountEntity savedAccountEntity = accountRepository.save(newAccountEntity);
    BusinessEntity newBusinessEntity = new BusinessEntity();
    newBusinessEntity.setName(requestBody.getBusinessName());
    newBusinessEntity.setAccount(savedAccountEntity);
    newBusinessEntity.setBetaTester(requestBody.getBetaTester() != null ? requestBody.getBetaTester() : false);
    newBusinessEntity.setContactName(requestBody.getContactName());
    newBusinessEntity.setEarlySignup(requestBody.getEarlySignup() != null ? requestBody.getEarlySignup() : false);
    BusinessEntity savedBusinessEntity = businessRepository.save(newBusinessEntity);
    authorizationService.login(requestBody.getEmail(), requestBody.getPassword());
    return savedBusinessEntity;
  }

  public FellowProfileData saveProfile(FellowProfileData requestBody, DataFetchingEnvironment environment) throws Exception {
    AccountEntity accountEntity = authorizationService.getAccount();
    FellowEntity fellowEntity = accountEntity.getFellow();
    FellowProfileEntity profileEntity = fellowEntity.getProfile();

    if (profileEntity == null) {
      profileEntity = new FellowProfileEntity();
      profileEntity.setFellow(fellowEntity);
    }

    convertBaseProfile(requestBody, profileEntity);
    convertExtendedProfileData(requestBody, profileEntity);
    profileEntity = profileRepository.save(profileEntity);
    FellowProfileData extendedProfileData = new FellowProfileData();
    String json = mapper.writeValueAsString(profileEntity);
    logger.info("Extended profile entity: " + json);
    convertBaseProfile(profileEntity, extendedProfileData);
    convertExtendedProfileEntity(profileEntity, extendedProfileData);
    json = mapper.writeValueAsString(extendedProfileData);
    logger.info("Extended profile data: " + json);
    return extendedProfileData;
  }

  private void convertExtendedProfileData(FellowProfileData in, FellowProfileEntity out) {
    if (in.getExperience() != null) {
      out.setExperience(in.getExperience().stream().map(data -> {
        ExperienceEntity e = this.convertProfileElementData(data, ExperienceEntity.class, experienceRepository);
        e.setProfile(out);
        experienceRepository.save(e);
        return e;
      }).collect(Collectors.toList()));
    }
    if (in.getEducation() != null) {
      out.setEducation(in.getEducation().stream().map(data -> {
        EducationEntity e = this.convertProfileElementData(data, EducationEntity.class, educationRepository);
        e.setProfile(out);
        educationRepository.save(e);
        return e;
      }).collect(Collectors.toList()));
    }
    if (in.getAwards() != null) {
      out.setAwards(in.getAwards().stream().map(data -> {
        AwardEntity e = this.convertProfileElementData(data, AwardEntity.class, awardRepository);
        e.setProfile(out);
        awardRepository.save(e);
        return e;
      }).collect(Collectors.toList()));
    }
    if (in.getExperienceLevels() != null) {
      out.setExperienceLevels(in.getExperienceLevels().stream().map(data -> {
        ExperienceLevelEntity e = this.convertProfileElementData(data, ExperienceLevelEntity.class, experienceLevelRepository);
        e.setProfile(out);
        experienceLevelRepository.save(e);
        return e;
      }).collect(Collectors.toList()));
    }
    if (in.getAccomplishments() != null) {
      out.setAccomplishments(in.getAccomplishments().stream().map(data -> {
        AccomplishmentEntity e = this.convertProfileElementData(data, AccomplishmentEntity.class, accomplishmentRepository);
        e.setProfile(out);
        accomplishmentRepository.save(e);
        return e;
      }).collect(Collectors.toList()));
    }
    if (in.getHobbies() != null) {
      out.setHobbies(in.getHobbies().stream().map(data -> {
        HobbyEntity e = this.convertProfileElementData(data, HobbyEntity.class, hobbyRepository);
        e.setProfile(out);
        hobbyRepository.save(e);
        return e;
      }).collect(Collectors.toList()));
    }
    if (in.getBookOrQuote() != null) {
      out.setBookOrQuote(in.getBookOrQuote().stream().map(data -> {
        BookOrQuoteEntity e = this.convertProfileElementData(data, BookOrQuoteEntity.class, bookOrQuoteRepository);
        e.setProfile(out);
        bookOrQuoteRepository.save(e);
        return e;
      }).collect(Collectors.toList()));
    }
    if (in.getLinks() != null) {
      out.setLinks(in.getLinks().stream().map(data -> {
        LinkEntity e = this.convertProfileElementData(data, LinkEntity.class, linkRepository);
        e.setProfile(out);
        linkRepository.save(e);
        return e;
      }).collect(Collectors.toList()));
    }
  }

  private <E extends BaseObject, D extends ProfileElementData>
    E convertProfileElementData(D data, Class<E> entityType,
      BaseRepository<E> repository) {
    try {
      String json = mapper.writeValueAsString(data);
      E e = mapper.readValue(json, entityType);
//      e.setId(data.getObjectId());
      if (e.getId() != null) {
        Optional<E> opt = repository.findById(e.getId());
        if (opt.isPresent()) {
          return opt.get();
        }
      }
      return repository.save(e);
    } catch (Exception ex) {
      return (E)null;
    }
  }

  public FellowProfileData getFellowProfile() {
    AccountEntity accountEntity = authorizationService.getAccount();
    FellowEntity fellowEntity = accountEntity.getFellow();
    FellowProfileEntity profileEntity = fellowEntity.getProfile();
    FellowProfileData extendedProfileData = new FellowProfileData();
    convertBaseProfile(profileEntity, extendedProfileData);
    convertExtendedProfileEntity(profileEntity, extendedProfileData);
    return extendedProfileData;
  }

  private void convertExtendedProfileEntity(FellowProfileEntity in, FellowProfileData out) {
    if (in.getExperience() != null) {
      out.setExperience(in.getExperience().stream().map(data -> {
        return this.convertProfileElementEntity(data, Experience.class);
      }).collect(Collectors.toList()));
    }
    if (in.getEducation() != null) {
      out.setEducation(in.getEducation().stream().map(data -> {
        return this.convertProfileElementEntity(data, Education.class);
      }).collect(Collectors.toList()));
    }
    if (in.getAwards() != null) {
      out.setAwards(in.getAwards().stream().map(data -> {
        return this.convertProfileElementEntity(data, Award.class);
      }).collect(Collectors.toList()));
    }
    if (in.getExperienceLevels() != null) {
      out.setExperienceLevels(in.getExperienceLevels().stream().map(data -> {
        return this.convertProfileElementEntity(data, ExperienceLevel.class);
      }).collect(Collectors.toList()));
    }
    if (in.getAccomplishments() != null) {
      out.setAccomplishments(in.getAccomplishments().stream().map(data -> {
        return this.convertProfileElementEntity(data, Accomplishment.class);
      }).collect(Collectors.toList()));
    }
    if (in.getHobbies() != null) {
      out.setHobbies(in.getHobbies().stream().map(data -> {
        return this.convertProfileElementEntity(data, Hobby.class);
      }).collect(Collectors.toList()));
    }
    if (in.getBookOrQuote() != null) {
      out.setBookOrQuote(in.getBookOrQuote().stream().map(data -> {
        return this.convertProfileElementEntity(data, BookOrQuote.class);
      }).collect(Collectors.toList()));
    }
    if (in.getLinks() != null) {
      out.setLinks(in.getLinks().stream().map(data -> {
        return this.convertProfileElementEntity(data, Link.class);
      }).collect(Collectors.toList()));
    }
  }

  private <E extends BaseObject, D extends ProfileElementData>
    D convertProfileElementEntity(E entity,
      Class<D> dataType) {
    try {
      String json = mapper.writeValueAsString(entity);
      D d = mapper.readValue(json, dataType);
//      d.setObjectId(entity.getId());
      return d;
    } catch (Exception ex) {
      return (D) null;
    }
  }

  private void convertBaseProfile(FellowProfile in, FellowProfile out) {
//    out.setObjectId(in.getObjectId());
    out.setSmallBio(in.getSmallBio());
    out.setCountry(in.getCountry());
    out.setLocation(in.getLocation());
    out.setSkills(in.getSkills());
    out.setJobTitles(in.getJobTitles());
    out.setPassions(in.getPassions());
    out.setLookingFor(in.getLookingFor());
    out.setPetDetails(in.getPetDetails());
    out.setAboutMe(in.getAboutMe());
    out.setLocationOptions(in.getLocationOptions());
    out.setLanguages(in.getLanguages());
  }

  public BusinessProfile saveBusinessProfile(BusinessProfile requestBody, DataFetchingEnvironment environment) throws Exception {
    AccountEntity accountEntity = authorizationService.getAccount();
    BusinessEntity businessEntity = accountEntity.getBusiness();
    BusinessProfileEntity profileEntity = businessEntity.getBusinessProfile();

    if (profileEntity == null) {
      profileEntity = new BusinessProfileEntity();
      profileEntity.setBusiness(businessEntity);
    }

    convertBusinessProfile(requestBody, profileEntity);
    profileEntity = businessProfileRepository.save(profileEntity);
    BusinessProfile businessProfileData = new BusinessProfile();
    String json = mapper.writeValueAsString(profileEntity);
    logger.info("Business profile entity: " + json);
    convertBusinessProfile(profileEntity, businessProfileData);
    json = mapper.writeValueAsString(businessProfileData);
    logger.info("Business profile data: " + json);
    return businessProfileData;
  }

  private void convertBusinessProfile(BusinessProfile in, BusinessProfile out) {
//    out.setObjectId(in.getObjectId());
    out.setSmallBio(in.getSmallBio());
    out.setCountry(in.getCountry());
    out.setLocation(in.getLocation());
    out.setWebsite(in.getWebsite());
    out.setBusinessField(in.getBusinessField());
    out.setMissionVision(in.getMissionVision());
    out.setMoreAboutBusiness(in.getMoreAboutBusiness());
    out.setBillingDetails(in.getBillingDetails());
    out.setAmountDue(in.getAmountDue());
  }

}
