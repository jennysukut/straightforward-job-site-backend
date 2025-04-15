package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.ConversationEntity;

@Repository
@Transactional
public interface ConversationRepository extends BaseRepository<ConversationEntity> {

}
