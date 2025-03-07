package com.sfjs.repo;

import com.sfjs.data.entity.AddressEntity;
import com.sfjs.jpa.repo.AddressRepository;

public class AddressRepositoryTest extends BaseRepositoryTest<AddressRepository, AddressEntity> {

  @Override
  protected AddressEntity createEntity() {
    return new AddressEntity();
  }

  // Add more specific tests for AddressRepository here
}