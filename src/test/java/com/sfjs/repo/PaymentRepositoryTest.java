package com.sfjs.repo;

import com.sfjs.crud.entity.PaymentEntity;
import com.sfjs.crud.repo.PaymentRepository;

public class PaymentRepositoryTest extends BaseRepositoryTest<PaymentRepository, PaymentEntity> {

  @Override
  protected PaymentEntity createEntity() {
    return new PaymentEntity();
  }

  // Add more specific tests for PaymentRepository here
}