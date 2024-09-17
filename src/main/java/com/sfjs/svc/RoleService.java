package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.dto.Role;
import com.sfjs.entity.RoleEntity;
import com.sfjs.persist.BasePersist;
import com.sfjs.persist.RolePersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService extends BaseService<RoleEntity, Role> {

  @Autowired
  RolePersist repository;

  public RoleService() {
    super(new BaseConverter<RoleEntity, Role>(RoleEntity.class, Role.class));
  }

  @Override
  public BasePersist<RoleEntity> getBaseRepository() {
    return this.repository;
  }
}
