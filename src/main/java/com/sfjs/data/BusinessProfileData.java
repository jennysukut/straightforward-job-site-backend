package com.sfjs.data;

import java.net.URL;

import lombok.Getter;
import lombok.Setter;

public class BusinessProfileData extends BaseData {

  @Getter @Setter String smallBio;
  @Getter @Setter String country;
  @Getter @Setter String location;
  @Getter @Setter URL website;
  @Getter @Setter String businessField;
  @Getter @Setter String missionVision;
  @Getter @Setter String moreAboutBusiness;
  @Getter @Setter String billingDetails;
  @Getter @Setter String amountDue;
}
