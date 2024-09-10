package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class ContactInfo extends BaseBody {

  @Getter
  @Setter
  private String website;

  @Getter
  @Setter
  private String phoneNumber;

  @Getter
  @Setter
  private String socials;
}
