package com.sfjs.gql.schema;

import java.net.URL;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Profile {

  @Getter
  @Setter
  public String firstName;

  @Getter
  @Setter
  public String lastName;

  @Getter
  @Setter
  public String email;

  @Getter
  @Setter
  public String password;

  @Getter
  @Setter
  public String smallBio;

  @Getter
  @Setter
  public String location;

  @Getter
  @Setter
  public List<String> skills;

  @Getter
  @Setter
  public List<String> jobTitles;

  @Getter
  @Setter
  public List<Experience> experiences;

  @Getter
  @Setter
  public List<Education> education;

  @Getter
  @Setter
  public List<AwardOrHonor> awardsAndHonors;

  @Getter
  @Setter
  public List<ExperienceLevel> experienceLevels;

  @Getter
  @Setter
  public List<Accomplishment> accomplishments;

  @Getter
  @Setter
  public URL personalWebsite;

  @Getter
  @Setter
  public URL portfolioWebsite;

  @Getter
  @Setter
  public List<URL> socialMediaLinks;

  @Getter
  @Setter
  public URL otherLink;

  @Getter
  @Setter
  public String passionateAbout;

  @Getter
  @Setter
  public String lookingFor;

  @Getter
  @Setter
  public String quirksAndHobbies;

  @Getter
  @Setter
  public String documents;

  @Getter
  @Setter
  public String favoriteBookOrQuote;

  @Getter
  @Setter
  public String moreAboutYou;

  public static class Experience {

    @Getter
    @Setter
    String title;

    @Getter
    @Setter

    String company;
    @Getter
    @Setter
    String yearOrYears;

    @Getter
    @Setter
    String details;
  }

  public static class Education {
    @Getter
    @Setter
    String degreeOrCertificate;

    @Getter
    @Setter
    String schoolName;

    @Getter
    @Setter
    String fieldOfStudy;
  }

  public static class AwardOrHonor {

    @Getter
    @Setter
    String awardOrHonor;

    @Getter
    @Setter
    String givenBy;

    @Getter
    @Setter
    String criteria;
  }

  public static class ExperienceLevel {

    @Getter
    @Setter
    String experienceLevel;

    @Getter
    @Setter
    String skill;

    @Getter
    @Setter
    String numberOfYears;
  }

  public static class Accomplishment {

    @Getter
    @Setter
    String accomplishment;

    @Getter
    @Setter
    String details;
  }
}
