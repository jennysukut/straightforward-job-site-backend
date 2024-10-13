package com.sfjs.conv;

import java.util.Arrays;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sfjs.crud.entity.BaseEntity;
import com.sfjs.crud.response.BaseResponse;

public class BaseConverter<ENTITY extends BaseEntity, BODY extends BaseResponse> {

  Logger logger = Logger.getLogger(getClass().getName());

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  private Class<BODY> bodyClass;

  public BaseConverter(Class<BODY> bodyClass) {
    this.bodyClass = bodyClass;
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
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

  public <E extends Enum<E>> E stringToEnum(Class<E> enumClass, String value) {
    if (value == null) {
      return null;
    }

    return Arrays.stream(enumClass.getEnumConstants()).filter(e -> e.name().equalsIgnoreCase(value)).findFirst()
        .orElse(null);
  }
}
