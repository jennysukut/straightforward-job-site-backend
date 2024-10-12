package com.sfjs.conv;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.sfjs.crud.entity.BusinessEntity;
import com.sfjs.crud.request.AccountRequest;
import com.sfjs.crud.request.BusinessRequest;
import com.sfjs.crud.request.RoleRequest;
import com.sfjs.crud.response.BusinessResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessConverter extends BaseConverter<BusinessEntity, BusinessRequest, BusinessResponse> {

  AccountConverter accountConverter;

  public BusinessConverter(AccountConverter accountConverter) {
    super(BusinessEntity.class, BusinessResponse.class);
    this.accountConverter = accountConverter;
  }

  /**
   * This maps a Business object to a BusinessEntity
   *
   * This function is necessary because the dto object is flattened; whereas, the
   * entity is not
   *
   * @param BusinessRequest object
   * @return BusinessEntity object
   */
  @Override
  public BusinessEntity convertToEntity(BusinessRequest src) {
    BusinessEntity dest = new BusinessEntity();

    dest.setName(src.getBusiness());
    AccountRequest account = new AccountRequest();
    account.setEmail(src.getEmail());
    // TODO consider getting rid of role entities
    RoleRequest role = new RoleRequest();
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
}
