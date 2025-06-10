package com.sfjs.data.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  @Getter @Setter private Set<Integer> stepsCompleted = new HashSet<>();

  public enum ProfileSteps {
    STEP_ONE(1),
    STEP_TWO(2),
    STEP_THREE(3),
    STEP_FOUR(4),
    STEP_FIVE(5),
    STEP_SIX(6);

    @Getter private final int value;

    ProfileSteps(int value) {
      this.value = value;
    }

    public static ProfileSteps fromValue(int value) {
      for (ProfileSteps step : ProfileSteps.values()) {
        if (step.value == value) {
          return step;
        }
      }
      throw new IllegalArgumentException("Invalid ProfileSteps value: " + value);
    }
  }
}
