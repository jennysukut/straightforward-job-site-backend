package com.sfjs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sfjs.entity.RoleEntity;
import com.sfjs.repo.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private RoleRepository roleRepository;

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
  }
}