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
   * Move entity to a managed state if not managed
   * Process all children then process entity
   *
   * @param entity
   * @return
   */
  public ENTITY customSave(ENTITY entity) {
    logger.info("Entering customSave: " + entity);
    entity = manageChildEntities(entity);
    if (!entityManager.contains(entity)) {
      logger.info("Entity is not managed: " + entity);
      if (entity.getId() == null) {
        logger.info("This entity is transient: " + entity);
        ENTITY existingEntity = this.customSearch(entity);
        if (existingEntity != null) {
          logger.info("Entity found in DB: " + existingEntity);
          entity = customMerge(entity, existingEntity);
          return entity;
        } else {
          logger.info("Transient entity not found in DB: " + entity);
          getBaseRepository().save(entity);
          return entity;
        }
      } else {
        logger.info("This entity is detached: " + entity);
        ENTITY e = entityManager.merge(entity);
        return e;
      }
    }
    return entity;
  }

  /**
   * Process all child entities to managed state
   *
   * This method should be overriden for entities with children
   *
   * @param entity - may or may not be in a managed state
   * @return entity - will be in a managed state
   */
  protected ENTITY manageChildEntities(ENTITY entity) {
    // Default behavior for entity with no child entities
    return entity;
  }

  /**
   * Set properties of existing entity if necessary
   *
   * This method should be overriden for entities with custom fields
   *
   * @param entity - contains property fields to set
   * @param existingEntity - contains existing state in DB
   * @return entity - with fields set appropriately
   */
  protected ENTITY customMerge(ENTITY entity, ENTITY existingEntity) {
    // Default behavior for entity without custom fields
    return getBaseRepository().save(existingEntity);
  }

  /**
   * Search for a transient entity in the database
   *
   * Override this method for entities with custom search
   *
   * @param entity - transient entity
   * @return entity - existing entity in DB or null if not found
   */
  protected ENTITY customSearch(ENTITY entity) {
    // Default behavior for searching for existing entity
    if (entity.getId() != null) {
      return getById(entity.getId());
    } else if (entity.getName() != null) {
      return findByName(entity.getName());
    } else if (entity.getLabel() != null) {
      return findByLabel(entity.getLabel());
    }
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
