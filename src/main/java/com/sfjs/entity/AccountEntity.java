package com.sfjs.entity;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import com.sfjs.dto.Account;
import com.sfjs.dto.BaseBody;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "account")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AccountEntity extends BaseEntity<AccountEntity, Account> {

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

  @Override
  public <B extends BaseBody<?, ?>> void refresh(B body) {
    super.refresh(body);
    if (body instanceof Account) {
      Account accountBody = (Account) body;
      if (accountBody.getEmail() != null) {
        this.setEmail(accountBody.getEmail());
      }
      if (accountBody.getPassword() != null) {
        this.setPassword(accountBody.getPassword());
      }
      if (accountBody.getRoles() != null) {
        this.setRoles(accountBody.getRoles().stream().map(role -> {
          RoleEntity roleEntity = new RoleEntity();
          roleEntity.setId(role.getId());
          return roleEntity;
        }).collect(Collectors.toSet()));
      } else {
        this.setRoles(Collections.emptySet());
      }
    }
  }
}
