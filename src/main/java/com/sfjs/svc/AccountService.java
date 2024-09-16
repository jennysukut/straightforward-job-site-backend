package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.persist.AccountPersist;
import com.sfjs.persist.BasePersist;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService extends BaseService<AccountEntity> {

  @Autowired
  AccountPersist repository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public BasePersist<AccountEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public AccountEntity save(AccountEntity entity) {
    if (entity.getPassword() != null) {
      String rawPassword = entity.getPassword();
      String encryptedPassword = passwordEncoder.encode(rawPassword);
      logger.info("Encrypted password: " + encryptedPassword);
      entity.setPassword(encryptedPassword);
    }
    return super.save(entity);
  }

  public AccountEntity findByEmail(String email) {
    AccountEntity entity = repository.findByEmail(email);
    return entity;
  }
}
