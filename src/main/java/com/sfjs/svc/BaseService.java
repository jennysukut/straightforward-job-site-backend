package com.sfjs.svc;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sfjs.entity.BaseEntity;
import com.sfjs.repo.BaseRepository;

public abstract class BaseService<ENTITY extends BaseEntity> {

  public abstract BaseRepository<ENTITY> getBaseRepository();

  public abstract JpaRepository<ENTITY, Long> getJpaRepository();

  public void delete(Long id) {
    getJpaRepository().deleteById(id);
  }

  public ENTITY save(ENTITY entity) {
    return getJpaRepository().save(entity);
  }

  public ENTITY getById(Long id) {
    return getJpaRepository().getReferenceById(id);
  }

  public ENTITY findById(Long id) {
    return getJpaRepository().findById(id).get();
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
    return getJpaRepository().findAll(pageable);
  }
}
