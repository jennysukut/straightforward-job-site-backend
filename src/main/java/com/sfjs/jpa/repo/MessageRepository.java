package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.MessageEntity;

@Repository
@Transactional
public interface MessageRepository extends BaseRepository<MessageEntity> {
}
