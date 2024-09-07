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

  // Static factory method
  public static <T extends BaseBody<?, ?>> T createInstance(Class<T> clazz) {
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Failed to create instance", e);
    }
  }

  public <E extends BaseEntity<?, ?>> void refresh(E entity) {
    if (entity.getId() != null) {
      this.setId(entity.getId());
    }
    if (entity.getName() != null) {
      this.setName(entity.getName());
    }
    if (entity.getLabel() != null) {
      this.setLabel(entity.getLabel());
    }
  }
}
