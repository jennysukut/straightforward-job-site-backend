package com.sfjs.dto;

import lombok.Getter;
import lombok.Setter;

public class BaseData {

  @Getter
  @Setter
  private Long id;

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String label;

  @Override
  public String toString() {
    return String.format("name: %s label: %s", name, label);
  }
}
