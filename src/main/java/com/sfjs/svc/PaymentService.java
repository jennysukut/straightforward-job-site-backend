package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.repo.AccountRepository;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService extends BaseService<PaymentEntity> {

  @Autowired
  PaymentRepository repository;

  @Autowired
  AccountRepository accountRepository;

  @Override
  public BaseRepository<PaymentEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public PaymentEntity save(PaymentEntity entity) {
    AccountEntity accountEntity = entity.getAccount();
    if (accountEntity.getId() == null) {
      accountEntity = accountRepository.save(accountEntity);
      entity.setAccount(accountEntity);
    } else {
      Long accountId = accountEntity.getId();
      accountEntity = accountRepository.findById(accountId).orElseThrow(); // Need to load the role
      entity.setAccount(accountEntity);
    }
    return super.save(entity);
  }
}
