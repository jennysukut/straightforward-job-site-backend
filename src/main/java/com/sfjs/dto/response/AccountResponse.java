package com.sfjs.dto.response;

import java.util.Set;

import com.sfjs.dto.request.RoleRequest;

import lombok.Getter;
import lombok.Setter;

public class AccountResponse extends BaseResponse {

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
