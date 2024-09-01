package com.sfjs.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "role")
public class RoleEntity {

  // Every entity needs an id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Getter
  @Setter
  private Long id;

  // Every entity needs a name
  @Getter
  @Setter
  String name;

  // Every entity needs a label
  @Getter
  @Setter
  private String label;

  @Getter
  @Setter
  @ManyToMany(mappedBy = "roles")
  private Set<AccountEntity> accounts = new HashSet<>();
}