package com.sfjs.crud.response;

import lombok.Getter;
import lombok.Setter;

public class FellowResponse extends BaseResponse {

  @Getter
  @Setter
  private Boolean collaborator;

  @Getter
  @Setter
  private String message;

  @Getter
  @Setter
  private Boolean referralPartner;

  @Getter
  @Setter
  private String referralCode;

  @Getter
  @Setter
  private Boolean betaTester;

  @Getter
  @Setter
  private AccountResponse account;
}
