package com.sfjs.data;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class JobApplicationNoteData extends BaseData {

  @Getter @Setter private String text;
  @Getter @Setter private boolean businessNote;
  @Getter @Setter private boolean fellowNote;
}
