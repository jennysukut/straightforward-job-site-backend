package com.sfjs.data.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.BaseObject;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "conversation")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConversationEntity extends BaseObject {

  @Getter
  @Setter
  @JsonIgnore
  @OneToMany(mappedBy = "conversation")
  private List<MessageEntity> messages = new ArrayList<>();

  @Getter
  @Setter
  @JsonIgnore
  @OneToOne(mappedBy = "conversation", optional = true)
  private JobApplicationEntity jobApplication;
}
