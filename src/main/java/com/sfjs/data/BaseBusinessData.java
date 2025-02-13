package com.sfjs.data;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseBusinessData extends BaseData {

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
}
