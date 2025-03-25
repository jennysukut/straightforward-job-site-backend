package com.sfjs.data.core;

import java.util.List;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains fields that can be shared between
 * entity and non-entity profile classes
 *
 * @author carl
 *
 */
@MappedSuperclass
public class FellowProfile extends BaseProfile {

  @Getter @Setter private List<String> skills;
  @Getter @Setter private List<String> jobTitles;
  @Getter @Setter private String passions;
  @Getter @Setter private String lookingFor;
  @Getter @Setter private String petDetails;
  @Getter @Setter private String aboutMe;
  @Getter @Setter private String shadow;
  @Getter @Setter private String favoriteBookOrQuote;
  @Getter @Setter private List<String> locationOptions;
  @Getter @Setter private List<String> languages;
}
