package com.sfjs.conv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sfjs.data.api.AccountData;
import com.sfjs.data.entity.AccountEntity;

@Service
public class AccountConverter extends BaseConverter<AccountEntity, AccountData> {

  @Autowired
  PasswordEncoder passwordEncoder;

  public AccountConverter() {
    super(AccountData.class);
  }
}
