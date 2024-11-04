package com.sfjs.gql.resolvers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sfjs.entity.FellowEntity;
import com.sfjs.repo.FellowRepository;

@RestController
@EnableWebMvc
@Transactional
public class FellowInfo {

  Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  FellowRepository fellowRepository;

  @QueryMapping(name = "fellows")
  public List<String> fellows(@Argument(name = "pageNumber") int pageNumber,
      @Argument(name = "pageSize") int pageSize) {
    return fellowRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream()
        .map(fellowEntity -> fellowEntity.getName()).toList();
  }
}
