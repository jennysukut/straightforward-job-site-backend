package com.sfjs.data.api;

import java.util.Set;

import com.sfjs.data.core.Account;

import lombok.Getter;
import lombok.Setter;

public class AccountData extends Account {

  @Getter
  @Setter
  private Set<RoleData> roles;
}
