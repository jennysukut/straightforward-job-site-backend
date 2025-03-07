package com.sfjs.jpa.repo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sfjs.data.entity.BookOrQuoteEntity;

@Repository
@Transactional
public interface BookOrQuoteRepository extends BaseRepository<BookOrQuoteEntity> {

}
