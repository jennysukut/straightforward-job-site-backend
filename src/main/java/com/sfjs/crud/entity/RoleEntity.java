package com.sfjs.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "role")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class RoleEntity extends BaseEntity {

  @Getter
  @Setter
  @JsonIgnore
  @ManyToMany(mappedBy = "roles")
  private Set<AccountEntity> accounts = new HashSet<>();
}