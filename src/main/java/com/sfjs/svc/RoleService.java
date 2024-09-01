package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sfjs.entity.RoleEntity;
import com.sfjs.repo.RoleRepository;
import com.sfjs.repo.SimpleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService extends SimpleService<RoleEntity> {

  @Autowired
  RoleRepository repository;

  @Override
  public SimpleRepository<RoleEntity> getSimpleRepository() {
    return this.repository;
  }

  @Override
  public JpaRepository<RoleEntity, Long> getJpaRepository() {
    return this.repository;
  }
}
