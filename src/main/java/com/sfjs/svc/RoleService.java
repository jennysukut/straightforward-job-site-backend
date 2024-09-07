package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.RoleEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService extends BaseService<RoleEntity> {

  @Autowired
  RoleRepository repository;

  @Override
  public BaseRepository<RoleEntity> getBaseRepository() {
    return this.repository;
  }
}
