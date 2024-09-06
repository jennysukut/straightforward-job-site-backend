package com.sfjs.repo;

import com.sfjs.entity.FellowEntity;

public class FellowRepositoryTest extends BaseRepositoryTest<FellowRepository, FellowEntity> {

  @Override
  protected FellowEntity createEntity() {
    return new FellowEntity();
  }

  // Add more specific tests for FellowRepository here
}