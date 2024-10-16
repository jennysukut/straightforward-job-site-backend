package com.sfjs.crud.svc;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.sfjs.conv.BaseConverter;
import com.sfjs.crud.entity.BaseEntity;
import com.sfjs.crud.repo.BaseRepository;
import com.sfjs.crud.response.BaseResponse;

public abstract class BaseService<ENTITY extends BaseEntity, BODY extends BaseResponse> {

  Logger logger = Logger.getLogger(getClass().getName());

  private BaseConverter<ENTITY, BODY> converter;

  public BaseService(BaseConverter<ENTITY, BODY> converter) {
    this.converter = converter;
  }

  protected BODY createBody(ENTITY entity) {
    return this.converter.convertToBody(entity);
  }

  public abstract BaseRepository<ENTITY> getBaseRepository();

  public Boolean delete(Long id) {
    getBaseRepository().deleteById(id);
    return true;
  }

  public BODY getById(Long id) {
    Optional<ENTITY> optional = getBaseRepository().findById(id);
    return this.createBody(optional.isPresent() ? optional.get() : null);
  }

  public BODY findById(Long id) {
    ENTITY entity = getBaseRepository().findById(id).get();
    return this.createBody(entity);
  }

  public List<BODY> findAllById(Long id) {
    List<ENTITY> entities = getBaseRepository().findAllById(id);
    return entities.stream().map(entity -> {
      return this.createBody(entity);
    }).collect(Collectors.toList());
  }

  public List<BODY> findAll() {
    List<ENTITY> entities = getBaseRepository().findAll();
    return entities.stream().map(entity -> {
      return this.createBody(entity);
    }).collect(Collectors.toList());
  }

  public BODY findByName(String name) {
    ENTITY entity = getBaseRepository().findByName(name);
    return this.createBody(entity);
  }

  public List<BODY> findAllByName(String name) {
    List<ENTITY> entities = getBaseRepository().findAllByName(name);
    return entities.stream().map(entity -> {
      return this.createBody(entity);
    }).collect(Collectors.toList());
  }

  public BODY findByLabel(String label) {
    ENTITY entity = getBaseRepository().findByLabel(label);
    return this.createBody(entity);
  }

  public List<BODY> findAllByLabel(String label) {
    List<ENTITY> entities = getBaseRepository().findAllByLabel(label);
    return entities.stream().map(entity -> {
      return this.createBody(entity);
    }).collect(Collectors.toList());
  }

  public Page<BODY> findAll(Optional<Integer> limit) {
    Pageable pageable = PageRequest.of(0, limit.isPresent() ? limit.get() : 3);
    Page<ENTITY> page = getBaseRepository().findAll(pageable);
    return page.map(entity -> this.createBody(entity));
  }
}
