package com.sfjs;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sfjs.data.entity.NumericMetricEntity;
import com.sfjs.data.entity.RoleEntity;
import com.sfjs.jpa.repo.NumericMetricRepository;
import com.sfjs.jpa.repo.RoleRepository;

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
      admin.setReference("ADMIN");
      admin.setDetails("Admin");
      roleRepository.save(admin);
      RoleEntity business = new RoleEntity();
      business.setReference("BUSINESS");
      business.setDetails("Business");
      roleRepository.save(business);
      RoleEntity fellow = new RoleEntity();
      fellow.setReference("FELLOW");
      fellow.setDetails("Fellow");
      roleRepository.save(fellow);
    }
    if (numericMetricRepository.count() == 0) {
      NumericMetricEntity fellowDonation = new NumericMetricEntity();
      fellowDonation.setReference("CURRENT_FELLOW_DONATION");
      fellowDonation.setMetric(BigDecimal.ZERO);
      numericMetricRepository.save(fellowDonation);
      NumericMetricEntity businessDonation = new NumericMetricEntity();
      businessDonation.setReference("CURRENT_BUSINESS_DONATION");
      businessDonation.setMetric(BigDecimal.ZERO);
      numericMetricRepository.save(businessDonation);
    }
  }
}
