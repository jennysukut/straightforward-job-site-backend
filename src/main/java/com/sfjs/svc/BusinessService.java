package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.BusinessEntity;
import com.sfjs.persist.BasePersist;
import com.sfjs.persist.BusinessPersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessService extends BaseService<BusinessEntity> {

  @Autowired
  BusinessPersist repository;

  @Override
  public BasePersist<BusinessEntity> getBaseRepository() {
    return this.repository;
  }
}
