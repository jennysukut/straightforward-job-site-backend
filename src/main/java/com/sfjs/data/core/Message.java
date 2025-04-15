package com.sfjs.data.core;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sfjs.data.BaseObject;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class Message extends BaseObject {

  @Getter
  @Setter
  @Column(name = "delivered_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime deliveredAt;

  @Getter
  @Setter
  @Column(name = "seen_at")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime seenAt;

  @Getter @Setter private boolean fromBusiness;

  @Getter @Setter private String text;
}
