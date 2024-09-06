package com.sfjs.repo;

import com.sfjs.entity.AccountEntity;

public class AccountRepositoryTest extends BaseRepositoryTest<AccountRepository, AccountEntity> {

  @Override
  protected AccountEntity createEntity() {
    return new AccountEntity();
  }

  // Add more specific tests for AccountRepository here
}