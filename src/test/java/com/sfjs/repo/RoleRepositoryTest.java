package com.sfjs.repo;

import com.sfjs.crud.entity.RoleEntity;
import com.sfjs.crud.repo.RoleRepository;

public class RoleRepositoryTest extends BaseRepositoryTest<RoleRepository, RoleEntity> {

  @Override
  protected RoleEntity createEntity() {
    return new RoleEntity();
  }

  // Add more specific tests for RoleRepository here
}