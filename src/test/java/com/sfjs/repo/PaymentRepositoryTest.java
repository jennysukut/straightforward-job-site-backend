package com.sfjs.repo;

import com.sfjs.entity.PaymentEntity;

public class PaymentRepositoryTest extends BaseRepositoryTest<PaymentRepository, PaymentEntity> {

  @Override
  protected PaymentEntity createEntity() {
    return new PaymentEntity();
  }

  // Add more specific tests for PaymentRepository here
}