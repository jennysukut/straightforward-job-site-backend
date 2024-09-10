package com.sfjs.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.entity.ContactInfoEntity;

@Repository
@Transactional
public interface ContactInfoRepository extends BaseRepository<ContactInfoEntity> {
}
