package com.sfjs.repo;

import java.util.List;

public interface SimpleRepository<E> {

  List<E> findAllById(Long id);

  boolean existsByName(String name);

  E findByName(String name);

  List<E> findAllByName(String name);

  boolean existsByLabel(String label);

  E findByLabel(String label);

  List<E> findAllByLabel(String label);

  List<E> findAll();
}
