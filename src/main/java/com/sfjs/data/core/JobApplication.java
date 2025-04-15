package com.sfjs.data.core;

import com.sfjs.data.BaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class JobApplication extends BaseObject {

  @Getter @Setter private String message;
  @Getter @Setter private String status;
  @Getter @Setter private boolean appIsBeingRejected;
  @Getter @Setter private String rejectionMessage;
  @Getter @Setter private String rejectionDetails;
  @Getter @Setter private boolean highlighted;
  @Getter @Setter private boolean jobOfferBeingSent;
}
