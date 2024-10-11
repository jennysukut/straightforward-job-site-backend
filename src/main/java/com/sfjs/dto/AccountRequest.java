package com.sfjs.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class AccountRequest extends BaseRequest {

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
  private Set<RoleRequest> roles;
}
