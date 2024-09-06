package com.sfjs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.entity.AccountEntity;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<AccountEntity, Long>, BaseRepository<AccountEntity> {
}
