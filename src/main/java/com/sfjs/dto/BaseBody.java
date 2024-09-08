package com.sfjs.dto;

import com.sfjs.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

public class BaseBody<BODY extends BaseBody<?, ?>, ENTITY extends BaseEntity<?, ?>> {

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
