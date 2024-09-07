package com.sfjs.entity;

import com.sfjs.dto.BaseBody;
import com.sfjs.dto.Fellow;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "fellow")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class FellowEntity extends BaseEntity<FellowEntity, Fellow> {

  @Getter
  @Setter
  private String firstName;

  @Getter
  @Setter
  private String lastName;

  @Override
  public <B extends BaseBody<?, ?>> void refresh(B body) {
    super.refresh(body);
    if (body instanceof Fellow) {
      Fellow fellowBody = (Fellow) body;
      if (fellowBody.getFirstName() != null) {
        this.setFirstName(fellowBody.getFirstName());
      }
      if (fellowBody.getLastName() != null) {
        this.setLastName(fellowBody.getLastName());
      }
    }
  }
}
