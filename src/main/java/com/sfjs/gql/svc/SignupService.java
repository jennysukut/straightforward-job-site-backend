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
import com.sfjs.crud.entity.AccomplishmentEntity;
import com.sfjs.crud.entity.AccountEntity;
import com.sfjs.crud.entity.AwardEntity;
import com.sfjs.crud.entity.BaseEntity;
import com.sfjs.crud.entity.BookOrQuoteEntity;
import com.sfjs.crud.entity.BusinessEntity;
import com.sfjs.crud.entity.EducationEntity;
import com.sfjs.crud.entity.ExperienceEntity;
import com.sfjs.crud.entity.ExperienceLevelEntity;
import com.sfjs.crud.entity.ExtendedProfileEntity;
import com.sfjs.crud.entity.FellowEntity;
import com.sfjs.crud.entity.HobbyEntity;
import com.sfjs.crud.entity.LinkEntity;
import com.sfjs.crud.entity.RoleEntity;
import com.sfjs.crud.repo.AccomplishmentRepository;
import com.sfjs.crud.repo.AccountRepository;
import com.sfjs.crud.repo.AwardRepository;
import com.sfjs.crud.repo.BaseRepository;
import com.sfjs.crud.repo.BookOrQuoteRepository;
import com.sfjs.crud.repo.BusinessRepository;
import com.sfjs.crud.repo.EducationRepository;
import com.sfjs.crud.repo.ExperienceLevelRepository;
import com.sfjs.crud.repo.ExperienceRepository;
import com.sfjs.crud.repo.FellowRepository;
import com.sfjs.crud.repo.HobbyRepository;
import com.sfjs.crud.repo.LinkRepository;
import com.sfjs.crud.repo.ProfileRepository;
import com.sfjs.crud.repo.RoleRepository;
import com.sfjs.data.AccomplishmentData;
import com.sfjs.data.AwardData;
import com.sfjs.data.BaseProfileData;
import com.sfjs.data.BookOrQuoteData;
import com.sfjs.data.EducationData;
import com.sfjs.data.ExperienceData;
import com.sfjs.data.ExperienceLevelData;
import com.sfjs.data.ExtendedProfileData;
import com.sfjs.data.HobbyData;
import com.sfjs.data.LinkData;
import com.sfjs.data.ProfileElementData;
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
    String email = requestBody.getEmail();
    AccountEntity existingAccountEntity = accountRepository.findByEmail(email);
    if (existingAccountEntity == null) {
      // This email has never been used
      FellowEntity savedFellowEntity = createNewFellowAndNewAccount(requestBody);
      return savedFellowEntity.getId();
    } else {
      // This account already exists, so make sure it's the same person
      try {
        authorizationService.login(requestBody.getEmail(), requestBody.getPassword());
      } catch (Exception ex) {
        logger.log(Level.INFO, "login", ex);
//        throw new IllegalArgumentException("Email is unavailable");
        throw ex;
      }
      FellowEntity existingFellowEntity = existingAccountEntity.getFellow();
      if (existingFellowEntity == null) {
        // No fellow
        FellowEntity savedFellowEntity = createNewFellow(requestBody, existingAccountEntity);
        return savedFellowEntity.getId();
      } else {
        String existingFellowName = existingFellowEntity.getName();
        if (existingFellowName != null && existingFellowName.contentEquals(requestBody.getName())) {
          // Same fellow
          FellowEntity savedFellowEntity = updateExistingFellow(requestBody, existingFellowEntity);
          return savedFellowEntity.getId();
        } else {
          // Different fellow
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
    return savedBusinessEntity;
  }

  public ExtendedProfileData saveProfile(ExtendedProfileData requestBody, DataFetchingEnvironment environment) throws Exception {
    AccountEntity accountEntity = authorizationService.getAccount();
    FellowEntity fellowEntity = accountEntity.getFellow();
    ExtendedProfileEntity profileEntity = fellowEntity.getProfile();

    if (profileEntity == null) {
      profileEntity = new ExtendedProfileEntity();
      profileEntity.setFellow(fellowEntity);
    }

    convertBaseProfile(requestBody, profileEntity);
    convertExtendedProfileData(requestBody, profileEntity);
    profileEntity = profileRepository.save(profileEntity);
    ExtendedProfileData extendedProfileData = new ExtendedProfileData();
    String json = mapper.writeValueAsString(profileEntity);
    logger.info("Extended profile entity: " + json);
    convertBaseProfile(profileEntity, extendedProfileData);
    convertExtendedProfileEntity(profileEntity, extendedProfileData);
    json = mapper.writeValueAsString(extendedProfileData);
    logger.info("Extended profile data: " + json);
    return extendedProfileData;
  }

  private void convertExtendedProfileData(ExtendedProfileData in, ExtendedProfileEntity out) {
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

  private <E extends BaseEntity, D extends ProfileElementData>
    E convertProfileElementData(D data, Class<E> entityType,
      BaseRepository<E> repository) {
    try {
      String json = mapper.writeValueAsString(data);
      E e = mapper.readValue(json, entityType);
      e.setId(data.getObjectId());
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

  public ExtendedProfileData getFellowProfile() {
    AccountEntity accountEntity = authorizationService.getAccount();
    FellowEntity fellowEntity = accountEntity.getFellow();
    ExtendedProfileEntity profileEntity = fellowEntity.getProfile();
    ExtendedProfileData extendedProfileData = new ExtendedProfileData();
    convertBaseProfile(profileEntity, extendedProfileData);
    convertExtendedProfileEntity(profileEntity, extendedProfileData);
    return extendedProfileData;
  }

  private void convertExtendedProfileEntity(ExtendedProfileEntity in, ExtendedProfileData out) {
    if (in.getExperience() != null) {
      out.setExperience(in.getExperience().stream().map(data -> {
        return this.convertProfileElementEntity(data, ExperienceData.class);
      }).collect(Collectors.toList()));
    }
    if (in.getEducation() != null) {
      out.setEducation(in.getEducation().stream().map(data -> {
        return this.convertProfileElementEntity(data, EducationData.class);
      }).collect(Collectors.toList()));
    }
    if (in.getAwards() != null) {
      out.setAwards(in.getAwards().stream().map(data -> {
        return this.convertProfileElementEntity(data, AwardData.class);
      }).collect(Collectors.toList()));
    }
    if (in.getExperienceLevels() != null) {
      out.setExperienceLevels(in.getExperienceLevels().stream().map(data -> {
        return this.convertProfileElementEntity(data, ExperienceLevelData.class);
      }).collect(Collectors.toList()));
    }
    if (in.getAccomplishments() != null) {
      out.setAccomplishments(in.getAccomplishments().stream().map(data -> {
        return this.convertProfileElementEntity(data, AccomplishmentData.class);
      }).collect(Collectors.toList()));
    }
    if (in.getHobbies() != null) {
      out.setHobbies(in.getHobbies().stream().map(data -> {
        return this.convertProfileElementEntity(data, HobbyData.class);
      }).collect(Collectors.toList()));
    }
    if (in.getBookOrQuote() != null) {
      out.setBookOrQuote(in.getBookOrQuote().stream().map(data -> {
        return this.convertProfileElementEntity(data, BookOrQuoteData.class);
      }).collect(Collectors.toList()));
    }
    if (in.getLinks() != null) {
      out.setLinks(in.getLinks().stream().map(data -> {
        return this.convertProfileElementEntity(data, LinkData.class);
      }).collect(Collectors.toList()));
    }
  }

  private <E extends BaseEntity, D extends ProfileElementData>
    D convertProfileElementEntity(E entity,
      Class<D> dataType) {
    try {
      String json = mapper.writeValueAsString(entity);
      D d = mapper.readValue(json, dataType);
      d.setObjectId(entity.getId());
      return d;
    } catch (Exception ex) {
      return (D) null;
    }
  }

  private void convertBaseProfile(BaseProfileData in, BaseProfileData out) {
    out.setObjectId(in.getObjectId());
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

}
