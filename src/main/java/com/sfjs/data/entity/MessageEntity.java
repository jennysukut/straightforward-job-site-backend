package com.sfjs.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sfjs.data.core.Message;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains the entity-specific declarations of fields
 * not sharable between entity and non-entity appliation message classes
 *
 * @author carl
 *
 */
@Entity(name = "message")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MessageEntity extends Message {

  @Getter
  @Setter
  @ManyToOne(optional = false)
  @JsonIgnore
  @JoinColumn(name = "conversation_id", unique = false)
  private ConversationEntity conversation;
}
