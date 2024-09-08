package com.sfjs.dto;

import java.util.Set;

import com.sfjs.entity.AccountEntity;

import lombok.Getter;
import lombok.Setter;

public class Account extends BaseBody<Account, AccountEntity> {

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String password;

  @Getter
  @Setter
  private boolean enabled;

  @Getter
  @Setter
  private Set<Role> roles;
}
