package com.sfjs.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.sfjs.entity.AccountEntity;

import lombok.Getter;
import lombok.Setter;

public class Account {

  @Getter
  @Setter
  private Long id;

  public Account() {

  }

  public Account(AccountEntity entity) {
    if (entity != null) {
      this.setId(entity.getId());
      this.setName(entity.getName());
      this.setLabel(entity.getLabel());
      this.setPassword(entity.getPassword());
      this.setEnabled(entity.isEnabled());
      this.setRoles(entity.getRoles().stream().map(roleEntity -> {
        Role role = new Role();
        role.setId(roleEntity.getId());
        role.setName(roleEntity.getName());
        role.setLabel(roleEntity.getLabel());
        return role;
      }).collect(Collectors.toSet()));
    }
  }

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String label;

  @Getter
  @Setter
  private String password;

  @Getter
  @Setter
  private Set<Role> roles;
  
  @Getter
  @Setter
  private boolean enabled;
}
