package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.PaymentEntity;

@Repository
@Transactional
public interface PaymentRepository extends BaseRepository<PaymentEntity> {
}
