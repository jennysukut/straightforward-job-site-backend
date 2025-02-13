package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.data.api.AccountData;
import com.sfjs.data.entity.AccountEntity;
import com.sfjs.jpa.repo.AccountRepository;
import com.sfjs.jpa.repo.BaseRepository;

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
public class AccountService extends BaseService<AccountEntity, AccountData> {

  @Autowired
  AccountRepository repository;

  @Override
  public BaseRepository<AccountEntity> getBaseRepository() {
    return this.repository;
  }

  public AccountService() {
    super(new BaseConverter<AccountEntity, AccountData>(AccountData.class));
  }

  public AccountEntity findByEmail(String email) {
    AccountEntity entity = repository.findByEmail(email);
    return entity;
  }
}
