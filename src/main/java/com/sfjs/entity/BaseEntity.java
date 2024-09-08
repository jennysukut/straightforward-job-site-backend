package com.sfjs.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sfjs.dto.BaseBody;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity<ENTITY extends BaseEntity<?, ?>, BODY extends BaseBody<?, ?>> {

  // Every entity needs an id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Getter
  @Setter
  private Long id;

  @Getter
  @Setter
  @Version
  private Long version;

  @Getter
  @Setter
  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Getter
  @Setter
  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Getter
  @Setter
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  public boolean isDeleted() {
    return this.getDeletedAt() != null;
  }

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

  @Override
  public String toString() {
    return String.format("ID: %d, Name: %s Label: %s Version: %d", this.id, this.name, this.label, this.version);
  }
}
