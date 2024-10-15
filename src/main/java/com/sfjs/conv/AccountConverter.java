package com.sfjs.conv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sfjs.crud.entity.AccountEntity;
import com.sfjs.crud.response.AccountResponse;

@Service
public class AccountConverter extends BaseConverter<AccountEntity, AccountResponse> {

  @Autowired
  PasswordEncoder passwordEncoder;

  public AccountConverter() {
    super(AccountResponse.class);
  }
}
