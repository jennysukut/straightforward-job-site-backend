package com.sfjs.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  @Override
  public BaseRepository<FellowEntity> getBaseRepository() {
    return this.repository;
  }
}
