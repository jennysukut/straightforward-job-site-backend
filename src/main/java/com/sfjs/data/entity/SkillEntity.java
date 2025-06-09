package com.sfjs.data.entity;

import com.sfjs.data.core.Skill;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity(name = "skill")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SkillEntity extends Skill {

}
