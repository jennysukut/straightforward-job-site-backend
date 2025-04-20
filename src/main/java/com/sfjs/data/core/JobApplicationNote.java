package com.sfjs.data.core;

import com.sfjs.data.BaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class JobApplicationNote extends BaseObject {

  @Getter @Setter private String note;
  @Getter @Setter private boolean madeByBusiness;
  @Getter @Setter private boolean madeByFellow;
}
