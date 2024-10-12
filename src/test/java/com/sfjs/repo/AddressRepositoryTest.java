package com.sfjs.repo;

import com.sfjs.crud.entity.AddressEntity;
import com.sfjs.crud.repo.AddressRepository;

public class AddressRepositoryTest extends BaseRepositoryTest<AddressRepository, AddressEntity> {

  @Override
  protected AddressEntity createEntity() {
    return new AddressEntity();
  }

  // Add more specific tests for AddressRepository here
}