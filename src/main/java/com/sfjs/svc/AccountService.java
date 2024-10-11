package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.dto.Account;
import com.sfjs.dto.response.AccountResponse;
import com.sfjs.entity.AccountEntity;
import com.sfjs.persist.AccountPersist;
import com.sfjs.persist.BasePersist;

import jakarta.transaction.Transactional;

/**
 * Handles business logic for service objects
 * 
 * Implements findByEmail method
 * 
 * @author carl
 *
 */
@Service
@Transactional
public class AccountService extends BaseService<AccountEntity, Account, AccountResponse> {

  @Autowired
  AccountPersist repository;

  @Override
  public BasePersist<AccountEntity> getBaseRepository() {
    return this.repository;
  }

  public AccountService() {
    super(new BaseConverter<AccountEntity, Account, AccountResponse>(AccountEntity.class, AccountResponse.class));
  }

  public AccountEntity findByEmail(String email) {
    AccountEntity entity = repository.findByEmail(email);
    return entity;
  }
}
