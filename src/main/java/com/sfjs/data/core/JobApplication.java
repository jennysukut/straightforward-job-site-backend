package com.sfjs.data.core;

import com.sfjs.data.BaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class JobApplication extends BaseObject {

  @Getter @Setter private String message;
  @Getter @Setter private String status;
}
