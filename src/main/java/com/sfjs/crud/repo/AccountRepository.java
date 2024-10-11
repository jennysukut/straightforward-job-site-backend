package com.sfjs.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.entity.AccountEntity;

@Repository
@Transactional
public interface AccountRepository extends BaseRepository<AccountEntity> {

  AccountEntity findByEmail(String email);
}
