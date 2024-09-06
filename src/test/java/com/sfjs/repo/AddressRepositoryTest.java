package com.sfjs.repo;

import com.sfjs.entity.AddressEntity;

public class AddressRepositoryTest extends BaseRepositoryTest<AddressRepository, AddressEntity> {

  @Override
  protected AddressEntity createEntity() {
    return new AddressEntity();
  }

  // Add more specific tests for AddressRepository here
}