package com.sfjs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.FellowEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.FellowRepository;

import jakarta.transaction.Transactional;

/**
 * This class does persistence for FellowEntity
 * 
 * Overrides customSave to call customSave on account entity
 *
 * @author carl
 *
 */
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
  protected FellowEntity manageChildEntities(FellowEntity entity) {
    AccountEntity e = accountPersist.customSave(entity.getAccount());
    entity.setAccount(e);
    return entity;
  }

  @Override
  protected FellowEntity customMerge(FellowEntity entity, FellowEntity existingEntity) {
    if (entity.getAccount().getId() != existingEntity.getAccount().getId()) {
      existingEntity.setAccount(entity.getAccount());
    }
    if (entity.getBetaTester() != existingEntity.getBetaTester()) {
      existingEntity.setBetaTester(entity.getBetaTester());
    }
    return existingEntity;
  }
}
