package com.sfjs.dto;

import com.sfjs.entity.FellowEntity;

import lombok.Getter;
import lombok.Setter;

public class Fellow extends BaseData {

  public Fellow() {
  }

  public Fellow(FellowEntity entity) {
    if (entity != null) {
      this.setId(entity.getId());
      this.setName(entity.getName());
      this.setLabel(entity.getLabel());
      this.setFirstName(entity.getFirstName());
      this.setLastName(entity.getLastName());
      this.setEmail(entity.getEmail());
      this.setPassword(entity.getPassword());
    }
  }

  @Getter
  @Setter
  private String firstName;

  @Getter
  @Setter
  private String lastName;

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String password;
}
