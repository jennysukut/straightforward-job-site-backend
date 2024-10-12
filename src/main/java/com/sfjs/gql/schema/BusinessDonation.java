package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class BusinessDonation {

  @Getter
  @Setter
  private String amount;

  @Getter
  @Setter
  private String businessName;

  @Getter
  @Setter
  private String contactName;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String referral;
}
