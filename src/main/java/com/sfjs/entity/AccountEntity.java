package com.sfjs.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.dto.Account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Entity(name = "account")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Proxy(lazy = false)
public class AccountEntity extends BaseEntity {

  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String password;

  @Getter
  @Setter
  private boolean enabled;

  @Getter
  @Setter
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles;

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PaymentEntity> payments = new ArrayList<>();
}
