package com.sfjs.persist;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.RoleEntity;
import com.sfjs.repo.AccountRepository;
import com.sfjs.repo.BaseRepository;

import jakarta.transaction.Transactional;

/**
 * This class does persistence for AccountEntity
 * 
 * Overrides customSave to call customSave on role entities
 * Overrides customSearch to search using email field
 * Implements findByEmail
 * @author carl
 *
 */
@Service
@Transactional
public class AccountPersist extends BasePersist<AccountEntity> {

  @Autowired
  AccountRepository repository;

  @Autowired
  RolePersist rolePersist;

  @Override
  public BaseRepository<AccountEntity> getBaseRepository() {
    return this.repository;
  }

  public AccountEntity findByEmail(String email) {
    AccountEntity entity = repository.findByEmail(email);
    return entity;
  }

  @Override
  public AccountEntity customSave(AccountEntity entity) {
    if (entity.getRoles() != null) {
      Set<RoleEntity> roleEntities = entity.getRoles().stream().map(roleEntity -> {
        return rolePersist.customSave(roleEntity);
      }).collect(Collectors.toSet());
      entity.setRoles(roleEntities);
    }
    return super.customSave(entity);
  }

  @Override
  protected AccountEntity customSearch(AccountEntity entity) {
    AccountEntity existingEntity = this.repository.findByEmail(entity.getEmail());
    if (existingEntity != null) {
      if (entity.getPassword() != null && existingEntity.getPassword() == null) {
        existingEntity.setPassword(entity.getPassword());
        existingEntity = this.save(existingEntity);
      }
    }
    return existingEntity;
  }
}
