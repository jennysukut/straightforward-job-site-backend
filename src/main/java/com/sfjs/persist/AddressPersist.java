package com.sfjs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AddressEntity;
import com.sfjs.repo.AddressRepository;
import com.sfjs.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressPersist extends BasePersist<AddressEntity> {

  @Autowired
  AddressRepository repository;

  @Override
  public BaseRepository<AddressEntity> getBaseRepository() {
    return this.repository;
  }
}
