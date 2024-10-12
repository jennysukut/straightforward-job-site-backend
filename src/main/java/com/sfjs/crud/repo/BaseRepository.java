package com.sfjs.crud.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.sfjs.crud.entity.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<ENTITY extends BaseEntity> extends JpaRepository<ENTITY, Long>, CrudRepository<ENTITY, Long> {

  @Override
  @Modifying
  @Query("UPDATE #{#entityName} e SET e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id")
  void deleteById(@Param("id") Long id);

  List<ENTITY> findAllById(Long id);

  boolean existsByName(String name);

  ENTITY findByName(String name);

  List<ENTITY> findAllByName(String name);

  boolean existsByLabel(String label);

  ENTITY findByLabel(String label);

  List<ENTITY> findAllByLabel(String label);

  @Override
  @Query("SELECT e FROM #{#entityName} e WHERE e.deletedAt IS NULL")
  List<ENTITY> findAll();
}
