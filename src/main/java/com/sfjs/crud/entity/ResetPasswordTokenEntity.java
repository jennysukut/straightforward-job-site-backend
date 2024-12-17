package com.sfjs.crud.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "reset_password_token")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ResetPasswordTokenEntity extends BaseEntity {

  @Getter
  @Setter
  private String token;

  @Getter
  @Setter
  @Column(name = "expires_at", nullable = false, updatable = false)
  private LocalDateTime expiresAt;

  @Getter
  @Setter
  private boolean used;

  @Getter
  @Setter
  @ManyToOne(optional = false)
  @JoinColumn(name = "account_id", unique = false)
  private AccountEntity account;
}
