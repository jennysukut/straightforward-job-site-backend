package com.sfjs.persist;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sfjs.entity.BaseEntity;
import com.sfjs.repo.BaseRepository;

import jakarta.persistence.EntityManager;

/**
 * Handles persistence for generic entities
 * 
 * Implements a custom save method
 * Delegates behavior to generic repositoy
 * 
 * @author carl
 *
 * @param <ENTITY>
 */
public abstract class BasePersist<ENTITY extends BaseEntity> {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private EntityManager entityManager;

  public abstract BaseRepository<ENTITY> getBaseRepository();

  /**
   * Special handling for saving an entity
   * 
   * If entity is already in the transaction, just save it
   * If not, try to find it in the DB
   * Save it
   * 
   * @param entity
   * @return
   */
  public ENTITY customSave(ENTITY entity) {
    if (entityManager.contains(entity)) {
      return getBaseRepository().save(entity);
    }

    ENTITY e = customSearch(entity);
    if (e != null) {
      return e;
    }

    if (entity.getName() != null) {
      e = getBaseRepository().findByName(entity.getName());
      if (e != null) {
        return e;
      }
    }

    if (entity.getLabel() != null) {
      e = getBaseRepository().findByLabel(entity.getLabel());
      if (e != null) {
        return e;
      }
    }

    // Save new entity
    return getBaseRepository().save(entity);
  }

  protected ENTITY customSearch(ENTITY entity) {
    // By default there is no custom search
    return null;
  }

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

  public Optional<ENTITY> findById(Long id) {
    return getBaseRepository().findById(id);
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
