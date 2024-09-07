package com.sfjs.dto;

import com.sfjs.entity.RoleEntity;

public class Role extends BaseData {

  public Role() {

  }

  public Role(RoleEntity entity) {
    if (entity != null) {
      this.setId(entity.getId());
      this.setName(entity.getName());
      this.setLabel(entity.getLabel());
    }
  }

}
