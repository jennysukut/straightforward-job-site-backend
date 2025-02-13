package com.sfjs.data.core;

import java.net.URL;

import com.sfjs.data.BaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BusinessProfile extends BaseObject {

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
