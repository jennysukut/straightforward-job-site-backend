package com.sfjs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.sfjs.data.entity.NumericMetricEntity;
import com.sfjs.data.entity.RoleEntity;
import com.sfjs.data.entity.SkillEntity;
import com.sfjs.jpa.repo.NumericMetricRepository;
import com.sfjs.jpa.repo.RoleRepository;
import com.sfjs.jpa.repo.SkillRepository;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private NumericMetricRepository numericMetricRepository;

  @Autowired
  private SkillRepository skillRepository;

  @Override
  public void run(String... args) throws Exception {
    if (roleRepository.count() == 0) {
      RoleEntity admin = new RoleEntity();
      admin.setName("ADMIN");
      admin.setLabel("Admin");
      roleRepository.save(admin);
      RoleEntity business = new RoleEntity();
      business.setName("BUSINESS");
      business.setLabel("Business");
      roleRepository.save(business);
      RoleEntity fellow = new RoleEntity();
      fellow.setName("FELLOW");
      fellow.setLabel("Fellow");
      roleRepository.save(fellow);
    }
    if (numericMetricRepository.count() == 0) {
      NumericMetricEntity fellowDonation = new NumericMetricEntity();
      fellowDonation.setName("CURRENT_FELLOW_DONATION");
      fellowDonation.setMetric(BigDecimal.ZERO);
      numericMetricRepository.save(fellowDonation);
      NumericMetricEntity businessDonation = new NumericMetricEntity();
      businessDonation.setName("CURRENT_BUSINESS_DONATION");
      businessDonation.setMetric(BigDecimal.ZERO);
      numericMetricRepository.save(businessDonation);
    }
    if (skillRepository.count() == 0) {
      ClassPathResource resource = new ClassPathResource("skillsList.ts");
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
        reader.lines()
          .filter(line -> {
            return line.trim().length() > 0 && line.trim().startsWith("\"");
          })
          .forEach(line -> {
            SkillEntity skillEntity = new SkillEntity();
            String name = line.trim().replaceAll("[\",]", "");
            skillEntity.setName(name);
            skillRepository.save(skillEntity);
          });
      }
    }
  }
}
