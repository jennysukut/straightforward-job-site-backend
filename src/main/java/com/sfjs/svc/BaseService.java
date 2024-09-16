package com.sfjs.svc;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.data.domain.Page;

import com.sfjs.entity.BaseEntity;
import com.sfjs.persist.BasePersist;

public abstract class BaseService<ENTITY extends BaseEntity> {

  Logger logger = Logger.getLogger(getClass().getName());

  public abstract BasePersist<ENTITY> getBaseRepository();

  public Boolean delete(Long id) {
    getBaseRepository().delete(id);
    return true;
  }

  public ENTITY save(ENTITY entity) {
    return getBaseRepository().customSave(entity);
  }

  public ENTITY getById(Long id) {
    return getBaseRepository().getById(id);
  }

  public ENTITY findById(Long id) {
    return getBaseRepository().findById(id).get();
  }

  public List<ENTITY> findAllById(Long id) {
    return getBaseRepository().findAllById(id);
  }

  public List<ENTITY> findAll() {
    return getBaseRepository().findAll();
  }

  public ENTITY findByName(String name) {
    ENTITY entity = getBaseRepository().findByName(name);
    return entity;
  }

  public List<ENTITY> findAllByName(String name) {
    return getBaseRepository().findAllByName(name);
  }

  public ENTITY findByLabel(String label) {
    return getBaseRepository().findByLabel(label);
  }

  public List<ENTITY> findAllByLabel(String label) {
    return getBaseRepository().findAllByLabel(label);
  }

  public Page<ENTITY> findAll(Optional<Integer> limit) {
    return getBaseRepository().findAll(limit);
  }
}
