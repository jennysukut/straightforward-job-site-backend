package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.repo.AccountRepository;
import com.sfjs.repo.SimpleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService extends SimpleService<AccountEntity> {

  @Autowired
  AccountRepository repository;

  @Override
  public SimpleRepository<AccountEntity> getSimpleRepository() {
    return this.repository;
  }

  @Override
  public JpaRepository<AccountEntity, Long> getJpaRepository() {
    return this.repository;
  }
}
