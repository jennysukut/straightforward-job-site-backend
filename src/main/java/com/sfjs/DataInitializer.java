package com.sfjs;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sfjs.crud.entity.NumericMetricEntity;
import com.sfjs.crud.entity.RoleEntity;
import com.sfjs.crud.repo.NumericMetricRepository;
import com.sfjs.crud.repo.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private NumericMetricRepository numericMetricRepository;

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
  }
}
