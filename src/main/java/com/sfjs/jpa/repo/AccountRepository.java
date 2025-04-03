package com.sfjs.jpa.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.AccountEntity;

@Repository
@Transactional
public interface AccountRepository extends BaseRepository<AccountEntity> {

  Optional<AccountEntity> findByEmail(String email);
}
