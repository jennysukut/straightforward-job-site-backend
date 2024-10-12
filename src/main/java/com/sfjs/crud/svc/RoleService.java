package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.crud.entity.RoleEntity;
import com.sfjs.crud.repo.BaseRepository;
import com.sfjs.crud.request.RoleRequest;
import com.sfjs.crud.response.RoleResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService extends BaseService<RoleEntity, RoleRequest, RoleResponse> {

  @Autowired
  BaseRepository<RoleEntity> repository;

  public RoleService() {
    super(new BaseConverter<RoleEntity, RoleRequest, RoleResponse>(RoleEntity.class, RoleResponse.class));
  }

  @Override
  public BaseRepository<RoleEntity> getBaseRepository() {
    return this.repository;
  }
}
