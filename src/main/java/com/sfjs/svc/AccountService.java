package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.repo.AccountRepository;
import com.sfjs.repo.BaseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService extends BaseService<AccountEntity> {

  @Autowired
  AccountRepository repository;

  @Override
  public BaseRepository<AccountEntity> getBaseRepository() {
    return this.repository;
  }
}
