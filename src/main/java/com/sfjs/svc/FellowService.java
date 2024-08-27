package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sfjs.entity.FellowEntity;
import com.sfjs.repo.FellowRepository;
import com.sfjs.repo.SimpleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowService extends SimpleService<FellowEntity> {

  @Autowired
  FellowRepository repository;

  @Override
  public SimpleRepository<FellowEntity> getSimpleRepository() {
    return this.repository;
  }

  @Override
  public JpaRepository<FellowEntity, Long> getJpaRepository() {
    return this.repository;
  }
}
