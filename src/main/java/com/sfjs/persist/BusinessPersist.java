package com.sfjs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.BusinessEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.BusinessRepository;

import jakarta.transaction.Transactional;

/**
 * This class does persistence for BusinessEntity
 * 
 * Overrides customSave to call customSave on account entities
 * 
 * @author carl
 *
 */
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
  protected BusinessEntity customMerge(BusinessEntity entity, BusinessEntity existingEntity) {
    {
      Boolean newProperty = entity.getBetaTester();
      Boolean existingProperty = existingEntity.getBetaTester();
      if (newProperty != null && existingProperty != newProperty) {
        existingEntity.setBetaTester(newProperty);
      }
    }
    {
      Boolean newProperty = entity.getEarlySignup();
      Boolean existingProperty = existingEntity.getEarlySignup();
      if (newProperty != null && existingProperty != newProperty) {
        existingEntity.setEarlySignup(newProperty);
      }
    }
    if (existingEntity.getAccount().getId() != entity.getAccount().getId()) {
      existingEntity.setAccount(entity.getAccount());
    }
    {
      String newProperty = entity.getContactName();
      String existingProperty = existingEntity.getContactName();
      if (newProperty != null && (existingProperty == null || existingProperty.contentEquals(newProperty))) {
        existingEntity.setContactName(newProperty);
      }
    }
    {
      String newProperty = entity.getReferral();
      String existingProperty = existingEntity.getReferral();
      if (newProperty != null && (existingProperty == null || existingProperty.contentEquals(newProperty))) {
        existingEntity.setReferral(newProperty);
      }
    }
    return super.customMerge(entity, existingEntity);
  }

  @Override
  protected BusinessEntity manageChildEntities(BusinessEntity entity) {
    AccountEntity e = accountPersist.customSave(entity.getAccount());
    entity.setAccount(e);
    return entity;
  }
}
