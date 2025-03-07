package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.ResetPasswordTokenEntity;

@Repository
@Transactional
public interface ResetPasswordTokenRepository
    extends BaseRepository<ResetPasswordTokenEntity> {
}
