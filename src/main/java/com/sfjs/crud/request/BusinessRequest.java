package com.sfjs.crud.request;

import lombok.Getter;
import lombok.Setter;

public class BusinessRequest extends BaseRequest {

  @Getter
  @Setter
  private String business;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private Boolean earlySignup;

  @Getter
  @Setter
  private Boolean betaTester;

  @Getter
  @Setter
  private String contactName;

  @Getter
  @Setter
  private String referral;

  @Getter
  @Setter
  private AccountRequest account;
}
