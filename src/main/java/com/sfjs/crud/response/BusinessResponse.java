package com.sfjs.crud.response;

import lombok.Getter;
import lombok.Setter;

public class BusinessResponse extends BaseResponse {

  @Getter
  @Setter
  private Boolean betaTester;

  @Getter
  @Setter
  private Boolean earlySignup;

  @Getter
  @Setter
  private String contactName;

  @Getter
  @Setter
  private String referral;

  @Getter
  @Setter
  private AccountResponse account;
}
