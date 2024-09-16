package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.FellowEntity;
import com.sfjs.persist.BasePersist;
import com.sfjs.persist.FellowPersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowService extends BaseService<FellowEntity> {

  @Autowired
  FellowPersist repository;

  @Override
  public BasePersist<FellowEntity> getBaseRepository() {
    return this.repository;
  }
}
