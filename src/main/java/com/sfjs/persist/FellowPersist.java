package com.sfjs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.FellowEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.FellowRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowPersist extends BasePersist<FellowEntity> {

  @Autowired
  FellowRepository repository;

  @Autowired
  AccountPersist accountPersist;

  @Override
  public BaseRepository<FellowEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public FellowEntity customSave(FellowEntity entity) {
    logger.info("Fellow override custom save entity: " + entity);

    // Account child entity
    AccountEntity accountEntity = entity.getAccount();
    accountEntity = accountPersist.customSave(accountEntity);
    logger.info("Account entity: " + accountEntity);
    entity.setAccount(accountEntity);
    return super.customSave(entity);
  }
}
