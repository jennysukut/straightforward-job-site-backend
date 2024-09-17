package com.sfjs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.PaymentRepository;

import jakarta.transaction.Transactional;

/**
 * This class does persistence for PaymentEntity
 * 
 * Overrides customSave to call customSave on account entity
 * @author carl
 *
 */
@Service
@Transactional
public class PaymentPersist extends BasePersist<PaymentEntity> {

  @Autowired
  PaymentRepository repository;

  @Autowired
  AccountPersist accountPersist;

  @Override
  public BaseRepository<PaymentEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public PaymentEntity customSave(PaymentEntity entity) {
    AccountEntity accountEntity = entity.getAccount();
    accountEntity = accountPersist.customSave(accountEntity);
    entity.setAccount(accountEntity);
    return super.customSave(entity);
  }
}
