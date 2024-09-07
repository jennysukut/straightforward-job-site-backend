package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.FellowEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.FellowRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowService extends BaseService<FellowEntity> {

  @Autowired
  FellowRepository repository;

  @Override
  public BaseRepository<FellowEntity> getBaseRepository() {
    return this.repository;
  }
}
