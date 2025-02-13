package com.sfjs.repo;

import com.sfjs.jpa.entity.RoleEntity;
import com.sfjs.jpa.repo.RoleRepository;

public class RoleRepositoryTest extends BaseRepositoryTest<RoleRepository, RoleEntity> {

  @Override
  protected RoleEntity createEntity() {
    return new RoleEntity();
  }

  // Add more specific tests for RoleRepository here
}