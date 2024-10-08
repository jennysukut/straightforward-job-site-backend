package com.sfjs.svc;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.FellowConverter;
import com.sfjs.dto.Fellow;
import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.FellowEntity;
import com.sfjs.entity.RoleEntity;
import com.sfjs.repo.AccountRepository;
import com.sfjs.repo.FellowRepository;
import com.sfjs.repo.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SignupService {

  @Autowired
  FellowConverter fellowConverter;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  FellowRepository fellowRepository;

  public Fellow signupFellow(Fellow requestBody) {
    String email = requestBody.getEmail();
    AccountEntity existingAccountEntity = accountRepository.findByEmail(email);
    if (existingAccountEntity == null) {
      // This email has never been used
      FellowEntity savedFellowEntity = createNewFellowAndNewAccount(requestBody);
      Fellow response = fellowConverter.convertToBody(savedFellowEntity);
      return response;
    } else {
      FellowEntity existingFellowEntity = existingAccountEntity.getFellow();
      if (existingFellowEntity == null) {
        // No fellow
        FellowEntity savedFellowEntity = createNewFellow(requestBody, existingAccountEntity);
        Fellow response = fellowConverter.convertToBody(savedFellowEntity);
        return response;
      } else {
        String existingFellowName = existingFellowEntity.getName();
        if (existingFellowName != null && existingFellowName.contentEquals(requestBody.getName())) {
          // Same fellow
          FellowEntity savedFellowEntity = updateExistingFellow(requestBody, existingFellowEntity);
          Fellow response = fellowConverter.convertToBody(savedFellowEntity);
          return response;
        } else {
          // Different fellow
          throw new IllegalArgumentException("Email is unavailable");
        }
      }
    }
  }

  private FellowEntity updateExistingFellow(Fellow requestBody, FellowEntity existingFellowEntity) {
    existingFellowEntity.setBetaTester(requestBody.getBetaTester());
    existingFellowEntity.setCollaborator(requestBody.isCollaborator());
    existingFellowEntity.setMessage(requestBody.getMessage());
    existingFellowEntity.setReferralCode(requestBody.getReferralCode());
    existingFellowEntity.setReferralPartner(requestBody.isReferralPartner());
    FellowEntity savedFellowEntity = fellowRepository.save(existingFellowEntity);
    return savedFellowEntity;
  }

  private FellowEntity createNewFellow(Fellow requestBody, AccountEntity existingAccountEntity) {
    // Create a new FellowEntity
    // Associate new fellow with existing account
    FellowEntity newFellowEntity = new FellowEntity();
    newFellowEntity.setAccount(existingAccountEntity);
    existingAccountEntity.setFellow(newFellowEntity);
    newFellowEntity.setBetaTester(requestBody.getBetaTester());
    newFellowEntity.setCollaborator(requestBody.isCollaborator());
    newFellowEntity.setMessage(requestBody.getMessage());
    newFellowEntity.setReferralCode(requestBody.getReferralCode());
    newFellowEntity.setReferralPartner(requestBody.isReferralPartner());
    FellowEntity savedFellowEntity = fellowRepository.save(newFellowEntity);
    return savedFellowEntity;
  }

  private FellowEntity createNewFellowAndNewAccount(Fellow requestBody) {
    // Create a new FellowEntity
    // Create a new AccountEntity
    RoleEntity fellowRoleEntity = roleRepository.findByName("FELLOW");
    AccountEntity newAccountEntity = new AccountEntity();
    newAccountEntity.setEmail(requestBody.getEmail());
    newAccountEntity.setEnabled(true);
    newAccountEntity.setRoles(Set.of(fellowRoleEntity));
    AccountEntity savedAccountEntity = accountRepository.save(newAccountEntity);
    FellowEntity newFellowEntity = new FellowEntity();
    newFellowEntity.setAccount(savedAccountEntity);
    newFellowEntity.setBetaTester(requestBody.getBetaTester());
    newFellowEntity.setCollaborator(requestBody.isCollaborator());
    newFellowEntity.setMessage(requestBody.getMessage());
    newFellowEntity.setName(requestBody.getName());
    newFellowEntity.setReferralCode(requestBody.getReferralCode());
    newFellowEntity.setReferralPartner(requestBody.isReferralPartner());
    FellowEntity savedFellowEntity = fellowRepository.save(newFellowEntity);
    return savedFellowEntity;
  }
}
