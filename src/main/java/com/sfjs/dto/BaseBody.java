package com.sfjs.dto;

import com.sfjs.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

public class BaseBody {

  @Getter
  @Setter
  private Long id;

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String label;

  public BaseBody() {
  }

  public BaseBody(BaseEntity entity) {
    this.setId(entity.getId());
    this.setName(entity.getName());
    this.setLabel(entity.getLabel());
  }

  @Override
  public String toString() {
    return String.format("id: %s name: %s label: %s", id, name, label);
  }
}
