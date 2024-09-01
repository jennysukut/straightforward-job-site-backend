package com.sfjs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.entity.RoleEntity;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, SimpleRepository<RoleEntity> {
}
