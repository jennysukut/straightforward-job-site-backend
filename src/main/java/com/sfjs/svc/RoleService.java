package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.dto.RoleRequest;
import com.sfjs.dto.response.RoleResponse;
import com.sfjs.entity.RoleEntity;
import com.sfjs.repo.BaseRepository;

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
