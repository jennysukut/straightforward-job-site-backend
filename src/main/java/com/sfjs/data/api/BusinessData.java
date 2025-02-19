package com.sfjs.data.api;

import com.sfjs.data.core.Business;

import lombok.Getter;
import lombok.Setter;

public class BusinessData extends Business {

  @Getter
  @Setter
  private AccountData account;
}
