package com.sfjs.dto.response;

import com.sfjs.dto.Account;

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
  private Account account;
}
