package com.sfjs.repo;

import com.sfjs.data.entity.AccountEntity;
import com.sfjs.jpa.repo.AccountRepository;

public class AccountRepositoryTest extends BaseRepositoryTest<AccountRepository, AccountEntity> {

  @Override
  protected AccountEntity createEntity() {
    return new AccountEntity();
  }

  // Add more specific tests for AccountRepository here
}