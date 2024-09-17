package com.sfjs.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

public class BaseBody {

  static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
  
  @Getter
  @Setter
  private Long id;

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String label;

  @Override
  public String toString() {
    try {
      return mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return e.getLocalizedMessage();
    }
  }
}
