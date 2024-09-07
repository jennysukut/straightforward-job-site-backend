package com.sfjs.ctrl;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfjs.dto.BaseBody;
import com.sfjs.entity.BaseEntity;
import com.sfjs.svc.BaseService;

@RestController
@EnableWebMvc
@Transactional
public abstract class BaseController<SERVICE extends BaseService<ENTITY>, ENTITY extends BaseEntity<?, ?>, BODY extends BaseBody<?, ?>> {

  @Autowired
  private SERVICE service;

  Logger logger = Logger.getLogger(getClass().getName());
  ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  protected abstract Class<BODY> getBodyClass();

  protected abstract Class<ENTITY> getEntityClass();

  protected BODY createBody(ENTITY entity) {
    BODY body = BaseBody.createInstance(getBodyClass());
    body.refresh(entity);
    return body;
  }

  protected ENTITY createEntity() {
    return BaseEntity.createInstance(getEntityClass());
  }

  public void delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
  }

  public BODY saveData(@Argument(name = "requestBody") BODY requestBody) {
    return save(requestBody);
  }

  public BODY save(@RequestBody BODY requestBody) {
    ENTITY entity;
    if (requestBody.getId() == null) {
      entity = createEntity();
    } else {
      entity = service.getById(requestBody.getId());
    }
    entity.refresh(requestBody);
    entity = service.save(entity);
    return createBody(entity);
  }

  public BODY getById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    ENTITY entity = service.getById(id);
    return createBody(entity);
  }

  public BODY findById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    ENTITY entity = service.findById(id);
    return createBody(entity);
  }

  public List<BODY> findAllById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return service.findAllById(id).stream().map(entity -> {
      return createBody(entity);
    }).collect(Collectors.toList());
  }

  public BODY findByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    ENTITY entity = service.findByName(name);
    return createBody(entity);
  }

  public List<BODY> findAllByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return service.findAllByName(name).stream().map(entity -> {
      return createBody(entity);
    }).collect(Collectors.toList());
  }

  public BODY findByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    ENTITY entity = service.findByLabel(label);
    return createBody(entity);
  }

  public List<BODY> findAllByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return service.findAllByLabel(label).stream().map(entity -> {
      return createBody(entity);
    }).collect(Collectors.toList());
  }

  public List<BODY> findAll(@Argument Integer limit) {
    logger.info("Request param limit: " + limit);
    Stream<ENTITY> roleStream = service.findAll().stream();
    if (limit != null) {
      roleStream = roleStream.limit(limit);
    }
    return roleStream.map(entity -> {
      return createBody(entity);
    }).collect(Collectors.toList());
  }
}
