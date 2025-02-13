package com.sfjs.repo;

import com.sfjs.jpa.entity.FellowEntity;
import com.sfjs.jpa.repo.FellowRepository;

public class FellowRepositoryTest extends BaseRepositoryTest<FellowRepository, FellowEntity> {

  @Override
  protected FellowEntity createEntity() {
    return new FellowEntity();
  }

  // Add more specific tests for FellowRepository here
}