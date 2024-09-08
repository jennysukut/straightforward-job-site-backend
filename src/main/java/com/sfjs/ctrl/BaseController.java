package com.sfjs.ctrl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfjs.dto.BaseBody;
import com.sfjs.entity.BaseEntity;
import com.sfjs.svc.BaseService;

@RestController
@EnableWebMvc
@Transactional
public abstract class BaseController<SERVICE extends BaseService<ENTITY>, ENTITY extends BaseEntity<?, ?>, BODY extends BaseBody<?, ?>> {

  public BaseController() {
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  private JavaType getType(int index) {
    Type genericSuperclass = getClass().getGenericSuperclass();
    if (genericSuperclass instanceof ParameterizedType) {
      Type[] typeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
      return mapper.getTypeFactory().constructType(typeArguments[index]);
    } else {
      throw new IllegalArgumentException("Type parameter is required.");
    }
  }

  private JavaType bodyType = getBodyClass();

  private JavaType entityType = getEntityClass();

  @Autowired
  private SERVICE service;

  Logger logger = Logger.getLogger(getClass().getName());
  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  protected JavaType getBodyClass() {
    return getType(2);
  }

  protected JavaType getEntityClass() {
    return getType(1);
  }

  private BODY createBody(ENTITY entity) {
    String json;
    try {
      json = mapper.writeValueAsString(entity);
      return mapper.readValue(json, bodyType);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  protected ENTITY createEntity(BODY body) {
    String json;
    try {
      json = mapper.writeValueAsString(body);
      return mapper.readValue(json, entityType);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public Boolean delete(@PathVariable(name = "id") Long id) {
    return service.delete(id);
  }

  public BODY save(@RequestBody BODY requestBody) {
    ENTITY entity;
    if (requestBody.getId() == null) {
      entity = createEntity(requestBody);
    } else {
      entity = service.getById(requestBody.getId());
      // TODO Merge entity and request body
      // entity.refresh(requestBody);
    }
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
