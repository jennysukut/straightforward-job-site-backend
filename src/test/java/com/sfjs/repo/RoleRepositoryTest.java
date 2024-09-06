package com.sfjs.repo;

import com.sfjs.entity.RoleEntity;

public class RoleRepositoryTest extends BaseRepositoryTest<RoleRepository, RoleEntity> {

  @Override
  protected RoleEntity createEntity() {
    return new RoleEntity();
  }

  // Add more specific tests for RoleRepository here
}