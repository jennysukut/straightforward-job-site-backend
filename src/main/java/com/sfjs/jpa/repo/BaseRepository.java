package com.sfjs.jpa.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.sfjs.data.BaseObject;

@NoRepositoryBean
public interface BaseRepository<ENTITY extends BaseObject>
    extends JpaRepository<ENTITY, Long>,
    CrudRepository<ENTITY, Long>, JpaSpecificationExecutor<ENTITY> {

  @Override
  @Modifying
  @Query("UPDATE #{#entityName} e SET e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id")
  void deleteById(@Param("id") Long id);

  @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deletedAt IS NULL")
  List<ENTITY> findAllById(Long id);

  @Override
  @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deletedAt IS NULL")
  Optional<ENTITY> findById(Long id);

//  boolean existsByReference(String reference);
//
//  ENTITY findByReference(String reference);
//
//  List<ENTITY> findAllByReference(String reference);
//
//  boolean existsByDetails(String details);
//
//  ENTITY findByDetails(String details);
//
//  List<ENTITY> findAllByDetails(String details);

  @Override
  @Query("SELECT e FROM #{#entityName} e WHERE e.deletedAt IS NULL")
  List<ENTITY> findAll();
}
