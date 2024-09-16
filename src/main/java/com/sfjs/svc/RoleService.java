package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.RoleEntity;
import com.sfjs.persist.BasePersist;
import com.sfjs.persist.RolePersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService extends BaseService<RoleEntity> {

  @Autowired
  RolePersist repository;

  @Override
  public BasePersist<RoleEntity> getBaseRepository() {
    return this.repository;
  }
}
