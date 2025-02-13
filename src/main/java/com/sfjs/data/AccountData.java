package com.sfjs.data;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class AccountData extends BaseAccountData {

  @Getter
  @Setter
  private Set<RoleData> roles;
}
