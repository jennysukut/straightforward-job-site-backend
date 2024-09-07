package com.sfjs.dto;

import com.sfjs.entity.BaseEntity;
import com.sfjs.entity.FellowEntity;

import lombok.Getter;
import lombok.Setter;

public class Fellow extends BaseBody<Fellow, FellowEntity> {

  @Getter
  @Setter
  private String firstName;

  @Getter
  @Setter
  private String lastName;

  @Override
  public <E extends BaseEntity<?, ?>> void refresh(E entity) {
    super.refresh(entity);
    if (entity instanceof FellowEntity) {
      FellowEntity fellowEntity = (FellowEntity) entity;
      if (fellowEntity.getFirstName() != null) {
        this.setFirstName(fellowEntity.getFirstName());
      }
      if (fellowEntity.getLastName() != null) {
        this.setLastName(fellowEntity.getLastName());
      }
    }
  }
}
