package com.sfjs.data.core;

import java.net.URL;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BusinessProfile extends BaseProfile {

  @Getter @Setter URL website;
  @Getter @Setter String businessField;
  @Getter @Setter String missionVision;
  @Getter @Setter String moreAboutBusiness;
  @Getter @Setter String billingDetails;
  @Getter @Setter String amountDue;
}
