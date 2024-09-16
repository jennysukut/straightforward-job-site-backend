package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.PaymentEntity;
import com.sfjs.persist.BasePersist;
import com.sfjs.persist.PaymentPersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService extends BaseService<PaymentEntity> {

  @Autowired
  PaymentPersist repository;

  @Override
  public BasePersist<PaymentEntity> getBaseRepository() {
    return this.repository;
  }
}
