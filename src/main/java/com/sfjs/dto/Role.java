package com.sfjs.dto;

import com.sfjs.entity.RoleEntity;

import lombok.Getter;
import lombok.Setter;

public class Role {

  @Getter
  @Setter
  private Long id;

  public Role() {

  }

  public Role(RoleEntity entity) {
    if (entity != null) {
      this.setId(entity.getId());
      this.setName(entity.getName());
      this.setLabel(entity.getLabel());
    }
  }

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String label;
}
