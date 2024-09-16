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
    logger.info("Account override custom save entity: " + entity);
    if (entity.getRoles() != null) {
      Set<RoleEntity> roleEntities = entity.getRoles().stream().map(roleEntity -> {
        return rolePersist.customSave(roleEntity);
      }).collect(Collectors.toSet());
      logger.info("Role entities: " + roleEntities);
      entity.setRoles(roleEntities);
    }
    return super.customSave(entity);
  }

  @Override
  protected AccountEntity customSearch(AccountEntity entity) {
    return this.repository.findByEmail(entity.getEmail());
  }
}
