package com.sfjs.data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class BaseObject {

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
  
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
  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private OffsetDateTime createdAt;

  @Getter
  @Setter
  @UpdateTimestamp
  @Column(name = "updated_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private OffsetDateTime updatedAt;

  @Getter
  @Setter
  @Column(name = "deleted_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime deletedAt;

  public boolean isDeleted() {
    return this.getDeletedAt() != null;
  }

  @Override
  public String toString() {
    try {
      return mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return e.getLocalizedMessage();
    }
  }

}
