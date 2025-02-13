package com.sfjs.jpa.entity;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.BaseAccountData;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "account")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AccountEntity extends BaseAccountData {

  @Getter
  @Setter
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "account_roles", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles;

  @Getter
  @Setter
  @JsonIgnore
  @OneToOne(mappedBy = "account")
  private BusinessEntity business;

  @Getter
  @Setter
  @JsonIgnore
  @OneToOne(mappedBy = "account", optional = true)
  private FellowEntity fellow;

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "account")
  private List<ResetPasswordTokenEntity> tokens;
}
