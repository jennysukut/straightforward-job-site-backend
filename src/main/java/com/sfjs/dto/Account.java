package com.sfjs.dto;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

public class Account extends BaseBody<Account, AccountEntity> {

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
  private Set<Role> roles;

  @Override
  public <E extends BaseEntity<?, ?>> void refresh(E entity) {
    super.refresh(entity);
    if (entity instanceof AccountEntity) {
      AccountEntity accountEntity = (AccountEntity) entity;
      if (accountEntity.getEmail() != null) {
        this.setEmail(accountEntity.getEmail());
      }
      if (accountEntity.getPassword() != null) {
        this.setPassword(accountEntity.getPassword());
      }
      if (accountEntity.getRoles() != null) {
        this.setRoles(accountEntity.getRoles().stream().map(roleEntity -> {
          Role role = new Role();
          role.refresh(roleEntity);
          return role;
        }).collect(Collectors.toSet()));
      } else {
        this.setRoles(Collections.emptySet());
      }
    }
  }
}
