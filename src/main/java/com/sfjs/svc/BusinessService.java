package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.repo.AccountRepository;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.BusinessRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessService extends BaseService<BusinessEntity> {

  @Autowired
  BusinessRepository repository;

  @Autowired
  AccountRepository accountRepository;

  @Override
  public BaseRepository<BusinessEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public BusinessEntity save(BusinessEntity entity) {
    if (entity.getAccount() != null) {
      Long accountId = entity.getAccount().getId();
      AccountEntity accountEntity = accountRepository.findById(accountId).orElseThrow(); // Need to load the role
      entity.setAccount(accountEntity);
    }
    return super.save(entity);
  }
}
