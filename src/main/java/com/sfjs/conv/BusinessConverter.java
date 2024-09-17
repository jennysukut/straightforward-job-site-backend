package com.sfjs.conv;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.sfjs.dto.Account;
import com.sfjs.dto.Business;
import com.sfjs.dto.Role;
import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.BusinessEntity;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessConverter extends BaseConverter<BusinessEntity, Business> {

  AccountConverter accountConverter;

  public BusinessConverter(AccountConverter accountConverter) {
    super(BusinessEntity.class, Business.class);
    this.accountConverter = accountConverter;
  }

  /**
   * This maps a Business object to a BusinessEntity
   * 
   * A Business object is flattened
   * A BusinessEntity object contains an Account object
   * 
   * @param Business object
   * @return BusinessEntity object
   */
  @Override
  public BusinessEntity convertToEntity(Business business) {
    // Step 1. Create the Account DTO
    Account account = new Account();
    account.setEmail(business.getEmail());
    account.setEnabled(true);
    account.setName(business.getUserName());
    account.setPassword(business.getPassword());
    // TODO consider getting rid of role entities
    Role role = new Role();
    role.setName("BUSINESS");
    account.setRoles(Set.of(role));

    // Step 2. Use the account convert to create account entity
    // This is where the password gets encrypted
    AccountEntity accountEntity = accountConverter.convertToEntity(account);

    // Step 3. Use the default conversion to create a business entity
    BusinessEntity entity = super.convertToEntity(business);

    // Step 4. Set the other fields of business entity
    entity.setAboutSection(business.getAboutSection());
    entity.setAccount(accountEntity);
    entity.setMissionAndVision(business.getMissionAndVision());
    entity.setName(business.getBusinessName());
    entity.setSmallBio(business.getSmallBio());
    entity.setWebsite(business.getWebsite());
    entity.setPhoneNumber(business.getPhoneNumber());
    entity.setSocials(business.getSocials());
    return entity;
  }

  @Override
  public Business convertToBody(BusinessEntity entity) {
    Business business = new Business();
    business.setBusinessName(entity.getName());
    AccountEntity accountEntity = entity.getAccount();
    business.setUserName(accountEntity.getName());
    // Password is not returned to the client
//    this.password = accountEntity.getPassword(); // Not passed to client
    business.setSmallBio(entity.getSmallBio());
    business.setMissionAndVision(entity.getMissionAndVision());
    business.setAboutSection(entity.getAboutSection());
    // TODO industry
//    this.industry = entity.getIndustry();
    // TODO location
//    AddressEntity address = null; // entity.getAddress();
//    this.location = String.format("%s %s", address.getCity(), address.getState());
    business.setWebsite(entity.getWebsite());
    business.setEmail(accountEntity.getEmail());
    business.setPhoneNumber(entity.getPhoneNumber());
    business.setSocials(entity.getSocials());
    return business;
  }
}
