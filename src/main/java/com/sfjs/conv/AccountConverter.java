package com.sfjs.conv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sfjs.dto.AccountRequest;
import com.sfjs.dto.response.AccountResponse;
import com.sfjs.entity.AccountEntity;

@Service
public class AccountConverter extends BaseConverter<AccountEntity, AccountRequest, AccountResponse> {

  @Autowired
  PasswordEncoder passwordEncoder;

  public AccountConverter() {
    super(AccountEntity.class, AccountResponse.class);
  }

  /**
   * This maps an Account dto to an AccountEntity
   *
   * This function is necessary to encrypt the password
   *
   * @param body - Account data transfer object
   * @return AccountEntity
   */
  @Override
  public AccountEntity convertToEntity(AccountRequest body) {
    AccountEntity entity = super.convertToEntity(body);
    if (body.getPassword() != null) {
      String rawPassword = body.getPassword();
      String encryptedPassword = passwordEncoder.encode(rawPassword);
      entity.setPassword(encryptedPassword);
    }
    return entity;
  }
}
