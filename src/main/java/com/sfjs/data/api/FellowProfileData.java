package com.sfjs.data.api;

import java.util.List;

import com.sfjs.data.core.Accomplishment;
import com.sfjs.data.core.Award;
import com.sfjs.data.core.BookOrQuote;
import com.sfjs.data.core.Education;
import com.sfjs.data.core.Experience;
import com.sfjs.data.core.ExperienceLevel;
import com.sfjs.data.core.FellowProfile;
import com.sfjs.data.core.Hobby;
import com.sfjs.data.core.Link;

import lombok.Getter;
import lombok.Setter;

/**
 * Contains the non-entity-specific declarations of fields
 * not sharable between entity and non-entity profile classes
 *
 * @author carl
 *
 */
public class FellowProfileData extends FellowProfile {

  @Getter @Setter private List<Experience> experience;
  @Getter @Setter private List<Education> education;
  @Getter @Setter private List<Award> awards;
  @Getter @Setter private List<ExperienceLevel> experienceLevels;
  @Getter @Setter private List<Accomplishment> accomplishments;
  @Getter @Setter private List<Hobby> hobbies;
  @Getter @Setter private List<BookOrQuote> bookOrQuote;
  @Getter @Setter private List<Link> links;
}
