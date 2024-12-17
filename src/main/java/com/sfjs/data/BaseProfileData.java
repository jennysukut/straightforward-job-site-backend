package com.sfjs.data;

import java.util.List;

import com.sfjs.crud.response.BaseResponse;

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
public class BaseProfileData extends BaseData {

  @Getter @Setter private String smallBio;
  @Getter @Setter private String country;
  @Getter @Setter private String location;
  @Getter @Setter private List<String> skills;
  @Getter @Setter private List<String> jobTitles;
  @Getter @Setter private String passions;
  @Getter @Setter private String lookingFor;
  @Getter @Setter private String petDetails;
  @Getter @Setter private String aboutMe;
  @Getter @Setter private String avatar;
  @Getter @Setter private String shadow;
  @Getter @Setter private String favoriteBookOrQuote;
  @Getter @Setter private List<String> locationOptions;
  @Getter @Setter private List<String> languages;
}
