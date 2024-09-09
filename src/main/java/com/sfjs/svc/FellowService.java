package com.sfjs.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sfjs.entity.AccountEntity;
import com.sfjs.entity.FellowEntity;
import com.sfjs.repo.AccountRepository;
import com.sfjs.repo.BaseRepository;
import com.sfjs.repo.FellowRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FellowService extends BaseService<FellowEntity> {

  @Autowired
  FellowRepository repository;

  @Autowired
  AccountRepository accountRepository;

  @Override
  public BaseRepository<FellowEntity> getBaseRepository() {
    return this.repository;
  }

  @Override
  public FellowEntity save(FellowEntity entity) {
    if (entity.getAccount() != null) {
      Long accountId = entity.getAccount().getId();
      AccountEntity accountEntity = accountRepository.findById(accountId).orElseThrow(); // Need to load the role
      entity.setAccount(accountEntity);
    }
    return super.save(entity);
  }
}
