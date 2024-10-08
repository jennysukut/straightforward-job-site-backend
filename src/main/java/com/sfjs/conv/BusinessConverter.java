package com.sfjs.conv;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.sfjs.dto.Account;
import com.sfjs.dto.Business;
import com.sfjs.dto.Role;
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
   * This function is necessary because the dto object is flattened; whereas, the
   * entity is not
   *
   * @param Business object
   * @return BusinessEntity object
   */
  @Override
  public BusinessEntity convertToEntity(Business src) {
    BusinessEntity dest = new BusinessEntity();

    dest.setName(src.getBusiness());
    Account account = new Account();
    account.setEmail(src.getEmail());
    // TODO consider getting rid of role entities
    Role role = new Role();
    role.setName("BUSINESS");
    account.setRoles(Set.of(role));
    // This is where the password gets encrypted
    dest.setAccount(accountConverter.convertToEntity(account));

    dest.setEarlySignup(src.getEarlySignup());
    dest.setBetaTester(src.getBetaTester());
    dest.setContactName(src.getContactName());
    dest.setReferral(src.getReferral());
    return dest;
  }

  /**
   * This maps a BusinessEntity object to a Business
   *
   * This function is necessary because the dto object is flattened; whereas, the
   * entity is not
   *
   * @param BusinessEntity object
   * @return Business object
   */
  @Override
  public Business convertToBody(BusinessEntity src) {
    Business dest = super.convertToBody(src);
    dest.setId(src.getId());
    dest.setBusiness(src.getName());
    dest.setEmail(src.getAccount().getEmail());
    dest.setEarlySignup(src.getEarlySignup());
    dest.setBetaTester(src.getBetaTester());
    dest.setContactName(src.getContactName());
    dest.setReferral(src.getReferral());

    return dest;
  }
}
