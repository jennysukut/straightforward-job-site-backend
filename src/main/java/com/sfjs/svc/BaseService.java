package com.sfjs.svc;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sfjs.entity.BaseEntity;
import com.sfjs.repo.BaseRepository;

public abstract class BaseService<ENTITY extends BaseEntity> {

  Logger logger = Logger.getLogger(getClass().getName());

  public abstract BaseRepository<ENTITY> getBaseRepository();

  public Boolean delete(Long id) {
    getBaseRepository().deleteById(id);
    return true;
  }

  public ENTITY save(ENTITY entity) {
    return getBaseRepository().save(entity);
  }

  public ENTITY getById(Long id) {
    return getBaseRepository().getReferenceById(id);
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
    Pageable pageable = PageRequest.of(0, limit.isPresent() ? limit.get() : 3);
    return getBaseRepository().findAll(pageable);
  }
}
