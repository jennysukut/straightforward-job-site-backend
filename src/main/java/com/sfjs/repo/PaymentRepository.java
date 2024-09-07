package com.sfjs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.entity.PaymentEntity;

@Repository
@Transactional
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>, BaseRepository<PaymentEntity> {
}
