package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.conv.BaseConverter;
import com.sfjs.dto.AccountRequest;
import com.sfjs.dto.response.AccountResponse;
import com.sfjs.entity.AccountEntity;
import com.sfjs.repo.AccountRepository;
import com.sfjs.repo.BaseRepository;

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
public class AccountService extends BaseService<AccountEntity, AccountRequest, AccountResponse> {

  @Autowired
  AccountRepository repository;

  @Override
  public BaseRepository<AccountEntity> getBaseRepository() {
    return this.repository;
  }

  public AccountService() {
    super(new BaseConverter<AccountEntity, AccountRequest, AccountResponse>(AccountEntity.class, AccountResponse.class));
  }

  public AccountEntity findByEmail(String email) {
    AccountEntity entity = repository.findByEmail(email);
    return entity;
  }
}
