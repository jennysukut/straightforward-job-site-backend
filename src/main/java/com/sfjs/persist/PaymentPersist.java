package com.sfjs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.BusinessEntity;
import com.sfjs.entity.FellowEntity;
import com.sfjs.entity.PaymentEntity;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.PaymentRepository;

import jakarta.transaction.Transactional;

/**
 * This class does persistence for PaymentEntity
 * 
 * Overrides customSave to call customSave on account entity
 *
 * @author carl
 *
 */
@Service
@Transactional
public class PaymentPersist extends BasePersist<PaymentEntity> {

  @Autowired
  PaymentRepository repository;

  @Autowired
  BusinessPersist businessPersist;

  @Autowired
  FellowPersist fellowPersist;

  @Override
  public BaseRepository<PaymentEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  protected PaymentEntity manageChildEntities(PaymentEntity entity) {
    if (entity.getBusiness() != null) {
      BusinessEntity e = businessPersist.customSave(entity.getBusiness());
      entity.setBusiness(e);
    } else if (entity.getFellow() != null) {
      FellowEntity e = fellowPersist.customSave(entity.getFellow());
      entity.setFellow(e);
    }
    return entity;
  }
}
