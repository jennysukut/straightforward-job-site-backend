package com.sfjs.gql.svc;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BusinessConverter;
import com.sfjs.conv.FellowConverter;
import com.sfjs.dto.BusinessRequest;
import com.sfjs.dto.FellowRequest;
import com.sfjs.dto.response.BusinessResponse;
import com.sfjs.dto.response.FellowResponse;
import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.entity.FellowEntity;
import com.sfjs.entity.RoleEntity;
import com.sfjs.repo.AccountRepository;
import com.sfjs.repo.BusinessRepository;
import com.sfjs.repo.FellowRepository;
import com.sfjs.repo.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SignupService {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  FellowConverter fellowConverter;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  FellowRepository fellowRepository;

  @Autowired
  BusinessConverter businessConverter;

  @Autowired
  BusinessRepository businessRepository;

  public FellowResponse signupFellow(FellowRequest requestBody) {
    String email = requestBody.getEmail();
    AccountEntity existingAccountEntity = accountRepository.findByEmail(email);
    if (existingAccountEntity == null) {
      // This email has never been used
      FellowEntity savedFellowEntity = createNewFellowAndNewAccount(requestBody);
      FellowResponse response = fellowConverter.convertToBody(savedFellowEntity);
      return response;
    } else {
      FellowEntity existingFellowEntity = existingAccountEntity.getFellow();
      if (existingFellowEntity == null) {
        // No fellow
        FellowEntity savedFellowEntity = createNewFellow(requestBody, existingAccountEntity);
        FellowResponse response = fellowConverter.convertToBody(savedFellowEntity);
        return response;
      } else {
        String existingFellowName = existingFellowEntity.getName();
        if (existingFellowName != null && existingFellowName.contentEquals(requestBody.getName())) {
          // Same fellow
          FellowEntity savedFellowEntity = updateExistingFellow(requestBody, existingFellowEntity);
          FellowResponse response = fellowConverter.convertToBody(savedFellowEntity);
          return response;
        } else {
          // Different fellow
          throw new IllegalArgumentException("Email is unavailable");
        }
      }
    }
  }

  private FellowEntity updateExistingFellow(FellowRequest requestBody, FellowEntity existingFellowEntity) {
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
  private FellowEntity createNewFellow(FellowRequest requestBody, AccountEntity existingAccountEntity) {
    // Create a new FellowEntity
    // Associate new fellow with existing account
    FellowEntity newFellowEntity = new FellowEntity();
    newFellowEntity.setName(requestBody.getName());
    newFellowEntity.setAccount(existingAccountEntity);
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
  private FellowEntity createNewFellowAndNewAccount(FellowRequest requestBody) {
    // Create a new FellowEntity
    // Create a new AccountEntity
    RoleEntity fellowRoleEntity = roleRepository.findByName("FELLOW");
    AccountEntity newAccountEntity = new AccountEntity();
    newAccountEntity.setEmail(requestBody.getEmail());
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
    return savedFellowEntity;
  }

  public BusinessResponse signupBusiness(BusinessRequest requestBody) {
    String email = requestBody.getEmail();
    AccountEntity existingAccountEntity = accountRepository.findByEmail(email);
    if (existingAccountEntity == null) {
      // This email has never been used
      BusinessEntity savedBusinessEntity = createNewBusinessAndNewAccount(requestBody);
      BusinessResponse response = businessConverter.convertToBody(savedBusinessEntity);
      return response;
    } else {
      Optional<BusinessEntity> existingBusinessEntity = existingAccountEntity.getBusinesses().stream()
          .filter(new Predicate<BusinessEntity>() {

            @Override
            public boolean test(BusinessEntity t) {
              String tName = t.getName();
              if (tName == null) {
                logger.info("business entity with null name: " + t);
                return false;
              }
              return t.getName().contentEquals(requestBody.getBusiness());
            }
          }).findFirst();
      if (!existingBusinessEntity.isPresent()) {
        // No Business with this name
        BusinessEntity savedBusinessEntity = createNewBusiness(requestBody, existingAccountEntity);
        BusinessResponse response = businessConverter.convertToBody(savedBusinessEntity);
        return response;
      } else {
        // There is a business with this name
        BusinessEntity savedBusinessEntity = updateExistingBusiness(requestBody, existingBusinessEntity.get());
        BusinessResponse response = businessConverter.convertToBody(savedBusinessEntity);
        return response;
      }
    }
  }

  private BusinessEntity updateExistingBusiness(BusinessRequest requestBody, BusinessEntity existingBusinessEntity) {
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

  private BusinessEntity createNewBusiness(BusinessRequest requestBody, AccountEntity existingAccountEntity) {
    // Create a new BusinessEntity
    // Associate new business with existing account
    BusinessEntity newBusinessEntity = new BusinessEntity();
    newBusinessEntity.setName(requestBody.getBusiness());
    newBusinessEntity.setAccount(existingAccountEntity);
//    existingAccountEntity.setBusiness(newBusinessEntity);
    newBusinessEntity.setBetaTester(requestBody.getBetaTester() != null ? requestBody.getBetaTester() : false);
    newBusinessEntity.setContactName(requestBody.getContactName());
    newBusinessEntity.setEarlySignup(requestBody.getEarlySignup() != null ? requestBody.getEarlySignup() : false);
    BusinessEntity savedBusinessEntity = businessRepository.save(newBusinessEntity);
    return savedBusinessEntity;
  }

  private BusinessEntity createNewBusinessAndNewAccount(BusinessRequest requestBody) {
    // Create a new BusinessEntity
    // Create a new AccountEntity
    RoleEntity businessRoleEntity = roleRepository.findByName("BUSINESS");
    AccountEntity newAccountEntity = new AccountEntity();
    newAccountEntity.setEmail(requestBody.getEmail());
    newAccountEntity.setEnabled(true);
    newAccountEntity.setRoles(Set.of(businessRoleEntity));
    AccountEntity savedAccountEntity = accountRepository.save(newAccountEntity);
    BusinessEntity newBusinessEntity = new BusinessEntity();
    newBusinessEntity.setName(requestBody.getBusiness());
    newBusinessEntity.setAccount(savedAccountEntity);
    newBusinessEntity.setBetaTester(requestBody.getBetaTester() != null ? requestBody.getBetaTester() : false);
    newBusinessEntity.setContactName(requestBody.getContactName());
    newBusinessEntity.setEarlySignup(requestBody.getEarlySignup() != null ? requestBody.getEarlySignup() : false);
    BusinessEntity savedBusinessEntity = businessRepository.save(newBusinessEntity);
    return savedBusinessEntity;
  }
}
