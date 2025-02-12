package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.jpa.entity.BookOrQuoteEntity;

@Repository
@Transactional
public interface BookOrQuoteRepository extends BaseRepository<BookOrQuoteEntity> {

}
