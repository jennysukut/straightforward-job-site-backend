package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.dto.Fellow;
import com.sfjs.entity.FellowEntity;
import com.sfjs.persist.BasePersist;
import com.sfjs.persist.FellowPersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowService extends BaseService<FellowEntity, Fellow> {

  @Autowired
  FellowPersist repository;

  public FellowService() {
    super(new BaseConverter<FellowEntity, Fellow>(FellowEntity.class, Fellow.class));
  }

  @Override
  public BasePersist<FellowEntity> getBaseRepository() {
    return this.repository;
  }
}
