package com.sfjs.crud.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.crud.entity.AccountEntity;
import com.sfjs.crud.repo.AccountRepository;
import com.sfjs.crud.repo.BaseRepository;
import com.sfjs.crud.response.AccountResponse;

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
public class AccountService extends BaseService<AccountEntity, AccountResponse> {

  @Autowired
  AccountRepository repository;

  @Override
  public BaseRepository<AccountEntity> getBaseRepository() {
    return this.repository;
  }

  public AccountService() {
    super(new BaseConverter<AccountEntity, AccountResponse>(AccountResponse.class));
  }

  public AccountEntity findByEmail(String email) {
    AccountEntity entity = repository.findByEmail(email);
    return entity;
  }
}
