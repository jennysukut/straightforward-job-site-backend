package com.sfjs.data.core;

import lombok.Getter;

public enum JobApplicationStatus {

  REJECTED("rejected"), HIGHLIGHTED("highlited"), OFFERED("offered");

  @Getter
  private final String value;

  JobApplicationStatus(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.getValue();
  }
}
