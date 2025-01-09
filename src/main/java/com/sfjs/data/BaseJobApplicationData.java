package com.sfjs.data;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseJobApplicationData extends BaseData {

  @Getter @Setter private String message;
  @Getter @Setter private String status;
}
