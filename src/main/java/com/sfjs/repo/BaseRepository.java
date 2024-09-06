package com.sfjs.repo;

import java.util.List;

import com.sfjs.entity.BaseEntity;

public interface BaseRepository<ENTITY extends BaseEntity> {

  List<ENTITY> findAllById(Long id);

  boolean existsByName(String name);

  ENTITY findByName(String name);

  List<ENTITY> findAllByName(String name);

  boolean existsByLabel(String label);

  ENTITY findByLabel(String label);

  List<ENTITY> findAllByLabel(String label);

  List<ENTITY> findAll();
}
