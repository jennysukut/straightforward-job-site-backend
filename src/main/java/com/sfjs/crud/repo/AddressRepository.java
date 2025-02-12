package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.AddressEntity;

@Repository
@Transactional
public interface AddressRepository extends BaseRepository<AddressEntity> {
}
