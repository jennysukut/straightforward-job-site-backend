package com.sfjs.gql.svc;

import java.net.URL;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.BusinessEntity;
import com.sfjs.data.entity.BusinessProfileEntity;
import com.sfjs.jpa.repo.AccountRepository;
import com.sfjs.jpa.repo.BusinessProfileRepository;
import com.sfjs.jpa.repo.BusinessRepository;
import com.sfjs.security.AuthorizationService;

import graphql.schema.DataFetchingEnvironment;

@Service
@Transactional
public class BusinessService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private AccountService accountService;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private AuthorizationService authorizationService;

  @Autowired
  private BusinessRepository businessRepository;

  @Autowired
  private BusinessProfileRepository businessProfileRepository;

  public Optional<BusinessEntity> businessSignup(
      @Argument(name = "email") String email,
      @Argument(name = "password") String password,
      @Argument(name = "name") String name,
      @Argument(name = "isBetaTester") Optional<Boolean> isBetaTester,
      @Argument(name = "contactName") String contactName,
      @Argument(name = "isEarlySignup") Optional<Boolean> isEarlySignup,
      @Argument(name = "referral") String referral) {
    logger.info(String.format("businessSignup: email [%s] name [%s]", email, name));
    return accountRepository.findByEmail(email).map(accountEntity -> {
      return Optional.of(accountEntity.getBusiness()).map(existingBusiness -> {
        logger.info("This account already exists, so make sure it's the same person");
        authorizationService.login(email, password);
        setBusinessFields(name, isBetaTester, contactName, isEarlySignup, referral, existingBusiness);
        return businessRepository.save(existingBusiness);
      }).orElseThrow(() -> {
        logger.info("No business associated with this account");
        throw new IllegalArgumentException("Email is unavailable");
      });
    }).or(() -> {
      return accountService.createNewBusinessAccount(email, password).map(account -> {
        authorizationService.login(email, password);
        BusinessEntity newEntity = new BusinessEntity();
        newEntity.setAccount(account);
        setBusinessFields(name, isBetaTester, contactName, isEarlySignup, referral, newEntity);
        return businessRepository.save(newEntity);
      });
    });
  }

  private void setBusinessFields(String name, Optional<Boolean> isBetaTester, String contactName, Optional<Boolean> isEarlySignup,
      String referral, BusinessEntity existingBusiness) {
    isBetaTester.ifPresent(value -> existingBusiness.setBetaTester(value));
    existingBusiness.setContactName(contactName);
    isEarlySignup.ifPresent(value -> existingBusiness.setEarlySignup(value));
    existingBusiness.setName(name);
    existingBusiness.setReferral(referral);
  }

  public Optional<BusinessEntity> getBusiness(Long id, DataFetchingEnvironment environment) {
    return businessRepository.findById(id);
  }

  public boolean saveBusinessProfilePage1(String smallBio, String country, String location,
      URL website, String avatar, DataFetchingEnvironment environment) {
    return authorizationService.getAccount().map(accountEntity -> {
      BusinessEntity businessEntity = accountEntity.getBusiness();
      BusinessProfileEntity businessProfileEntity = businessEntity.getBusinessProfile();

      if (businessProfileEntity == null) {
        businessProfileEntity = new BusinessProfileEntity();
        businessProfileEntity.setBusiness(businessEntity);
      }

      businessProfileEntity.setSmallBio(smallBio);
      businessProfileEntity.setCountry(country);
      businessProfileEntity.setLocation(location);
      businessProfileEntity.setWebsite(website);
      businessProfileEntity.setAvatar(avatar);
      businessProfileRepository.save(businessProfileEntity);
      return true;
    }).orElseThrow(() -> new InternalError());
  }

  public boolean saveBusinessProfilePage2(String businessField, String missionVision, String moreAboutBusiness,
      DataFetchingEnvironment environment) {
    return authorizationService.getAccount().map(accountEntity -> {
      BusinessEntity businessEntity = accountEntity.getBusiness();
      final BusinessProfileEntity businessProfileEntity = businessEntity.getBusinessProfile();

      if (businessProfileEntity == null) {
        logger.info("No profile associated with this business account");
        throw new IllegalArgumentException("No profile for this business account");
      }

      businessProfileEntity.setBusinessField(businessField);
      businessProfileEntity.setMissionVision(missionVision);
      businessProfileEntity.setMoreAboutBusiness(moreAboutBusiness);
      businessProfileRepository.save(businessProfileEntity);
      return true;
    }).orElseThrow(() -> new InternalError());
  }
}
