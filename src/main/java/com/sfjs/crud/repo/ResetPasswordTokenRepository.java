package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.ResetPasswordTokenEntity;

@Repository
@Transactional
public interface ResetPasswordTokenRepository
    extends BaseRepository<ResetPasswordTokenEntity> {
}
