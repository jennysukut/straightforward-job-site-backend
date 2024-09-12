package com.sfjs.dto;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseConverter<S, T> implements Converter<S, T> {

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  private Class<T> clazz;

  public BaseConverter(Class<T> clazz) {
    this.clazz = clazz;
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Override
  public T convert(S src) {
    String json;
    try {
      json = mapper.writeValueAsString(src);
      return mapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }

}
