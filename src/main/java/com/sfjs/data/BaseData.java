package com.sfjs.data;

import com.sfjs.jpa.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseData extends BaseEntity {

  @Getter @Setter private Long objectId;
}
