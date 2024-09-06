package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AddressEntity;
import com.sfjs.repo.AddressRepository;
import com.sfjs.repo.SimpleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressService extends SimpleService<AddressEntity> {

  @Autowired
  AddressRepository repository;

  @Override
  public SimpleRepository<AddressEntity> getSimpleRepository() {
    return this.repository;
  }

  @Override
  public JpaRepository<AddressEntity, Long> getJpaRepository() {
    return this.repository;
  }
}
