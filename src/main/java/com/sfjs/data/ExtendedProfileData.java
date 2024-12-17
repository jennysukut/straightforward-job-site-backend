package com.sfjs.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Contains the non-entity-specific declarations of fields
 * not sharable between entity and non-entity profile classes
 *
 * @author carl
 *
 */
public class ExtendedProfileData extends BaseProfileData {

  @Getter @Setter private List<ExperienceData> experience;
  @Getter @Setter private List<EducationData> education;
  @Getter @Setter private List<AwardData> awards;
  @Getter @Setter private List<ExperienceLevelData> experienceLevels;
  @Getter @Setter private List<AccomplishmentData> accomplishments;
  @Getter @Setter private List<HobbyData> hobbies;
  @Getter @Setter private List<BookOrQuoteData> bookOrQuote;
  @Getter @Setter private List<LinkData> links;
}
