package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class Business extends BaseBody {

  @Getter
  @Setter
  private Account account;

  @Getter
  @Setter
  private String smallBio;

  @Getter
  @Setter
  private String missionAndVision;

  @Getter
  @Setter
  private String aboutSection;
}
