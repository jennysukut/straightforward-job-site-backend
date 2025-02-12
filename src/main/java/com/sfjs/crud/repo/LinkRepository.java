package com.sfjs.crud.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.LinkEntity;

@Repository
@Transactional
public interface LinkRepository extends BaseRepository<LinkEntity> {

}
