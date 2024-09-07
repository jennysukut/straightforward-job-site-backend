package com.sfjs.entity;

import com.sfjs.dto.BaseBody;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseEntity<ENTITY extends BaseEntity<?, ?>, BODY extends BaseBody<?, ?>> {

  // Every entity needs an id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Getter
  @Setter
  private Long id;

  // Every entity needs a name
  @Getter
  @Setter
  String name;

  // Every entity needs a label
  @Getter
  @Setter
  private String label;

  public static <T extends BaseEntity<?, ?>> T createInstance(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Failed to create instance", e);
    }
  }

  public <B extends BaseBody<?, ?>> void refresh(B body) {
    if (body.getId() != null) {
      this.setId(body.getId());
    }
    if (body.getName() != null) {
      this.setName(body.getName());
    }
    if (body.getLabel() != null) {
      this.setLabel(body.getLabel());
    }
  }
}
