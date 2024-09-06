package com.sfjs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.entity.AddressEntity;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<AddressEntity, Long>, BaseRepository<AddressEntity> {
}
