package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.PaymentEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService extends BaseService<PaymentEntity> {

  @Autowired
  PaymentRepository repository;

  @Override
  public BaseRepository<PaymentEntity> getBaseRepository() {
    return this.repository;
  }
}
