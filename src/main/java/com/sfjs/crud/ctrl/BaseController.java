package com.sfjs.crud.ctrl;

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

import com.sfjs.crud.entity.BaseEntity;
import com.sfjs.crud.request.BaseRequest;
import com.sfjs.crud.response.BaseResponse;
import com.sfjs.crud.svc.BaseService;

@RestController
@EnableWebMvc
@Transactional
public abstract class BaseController<SERVICE extends BaseService<ENTITY, REQUEST, BODY>, ENTITY extends BaseEntity, REQUEST extends BaseRequest, BODY extends BaseResponse> {

  @Autowired
  private SERVICE service;

  Logger logger = Logger.getLogger(getClass().getName());

  public Boolean delete(@PathVariable(name = "id") Long id) {
    return service.delete(id);
  }

  public BODY save(@RequestBody REQUEST requestBody) {
    return service.save(requestBody);
  }

  public BODY getById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return service.getById(id);
  }

  public BODY findById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return service.findById(id);
  }

  public List<BODY> findAllById(@PathVariable("id") Long id) {
    logger.info("Path variable: " + id);
    return service.findAllById(id);
  }

  public BODY findByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return service.findByName(name);
  }

  public List<BODY> findAllByName(@PathVariable("name") String name) {
    logger.info("Path variable: " + name);
    return service.findAllByName(name);
  }

  public BODY findByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return service.findByLabel(label);
  }

  public List<BODY> findAllByLabel(@PathVariable("label") String label) {
    logger.info("Path variable: " + label);
    return service.findAllByLabel(label);
  }

  public List<BODY> findAll(@Argument(name = "limit") Integer limit) {
    logger.info("Request param limit: " + limit);
    Stream<BODY> stream = service.findAll().stream();
    if (limit != null) {
      stream = stream.limit(limit);
    }
    return stream.collect(Collectors.toList());
  }
}
