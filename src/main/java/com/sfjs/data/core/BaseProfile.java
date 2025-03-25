package com.sfjs.data.core;

import com.sfjs.data.BaseObject;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseProfile extends BaseObject {

  @Getter @Setter private String smallBio;
  @Getter @Setter private String country;
  @Getter @Setter private String location;
  @Getter @Setter private String avatar;
}
