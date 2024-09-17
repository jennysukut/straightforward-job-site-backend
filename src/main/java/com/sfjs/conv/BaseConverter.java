package com.sfjs.conv;

import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfjs.dto.BaseBody;
import com.sfjs.entity.BaseEntity;

public class BaseConverter<ENTITY extends BaseEntity, BODY extends BaseBody> {

  Logger logger = Logger.getLogger(getClass().getName());

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  private Class<ENTITY> entityClass;
  private Class<BODY> bodyClass;

  public BaseConverter(Class<ENTITY> entityClass, Class<BODY> bodyClass) {
    this.entityClass = entityClass;
    this.bodyClass = bodyClass;
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public ENTITY convertToEntity(BODY in) {
    String json;
    try {
      json = mapper.writeValueAsString(in);
      return mapper.readValue(json, entityClass);
    } catch (JsonProcessingException jpe) {
      throw new IllegalArgumentException(jpe);
    }
  }

  public BODY convertToBody(ENTITY in) {
    String json;
    try {
      json = mapper.writeValueAsString(in);
      return mapper.readValue(json, bodyClass);
    } catch (JsonProcessingException jpe) {
      throw new IllegalArgumentException(jpe);
    }
  }

}
