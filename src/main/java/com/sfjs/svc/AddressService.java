package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AddressEntity;
import com.sfjs.repo.AddressRepository;
import com.sfjs.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressService extends BaseService<AddressEntity> {

  @Autowired
  AddressRepository repository;

  @Override
  public BaseRepository<AddressEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public JpaRepository<AddressEntity, Long> getJpaRepository() {
    return this.repository;
  }
}
