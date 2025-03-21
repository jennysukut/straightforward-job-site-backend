package com.sfjs.data;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class NamedBaseObject extends BaseObject {

  // Every entity needs a name
  @Getter
  @Setter
  @Column(name = "name")
  private String name;

  // Every entity needs a label
  @Getter
  @Setter
  @Column(name = "label")
  private String label;
}
