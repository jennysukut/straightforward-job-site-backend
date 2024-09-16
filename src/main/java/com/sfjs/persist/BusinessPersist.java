package com.sfjs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.BusinessRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessPersist extends BasePersist<BusinessEntity> {

  @Autowired
  BusinessRepository repository;

  @Autowired
  AccountPersist accountPersist;

  @Override
  public BaseRepository<BusinessEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public BusinessEntity customSave(BusinessEntity entity) {
    logger.info("Business override custom save entity: " + entity);

    // Account child entity
    AccountEntity accountEntity = entity.getAccount();
    accountEntity = accountPersist.customSave(accountEntity);
    logger.info("Account entity: " + accountEntity);
    entity.setAccount(accountEntity);

    return super.customSave(entity);
  }
}
