package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.data.RoleData;
import com.sfjs.jpa.entity.RoleEntity;
import com.sfjs.jpa.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService extends BaseService<RoleEntity, RoleData> {

  @Autowired
  BaseRepository<RoleEntity> repository;

  public RoleService() {
    super(new BaseConverter<RoleEntity, RoleData>(RoleData.class));
  }

  @Override
  public BaseRepository<RoleEntity> getBaseRepository() {
    return this.repository;
  }
}
