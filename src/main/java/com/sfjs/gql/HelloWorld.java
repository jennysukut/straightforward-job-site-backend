package com.sfjs.gql;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
@Transactional
public class HelloWorld {

  @QueryMapping(name = "helloWorld")
  public String helloWorld() {
    return "Hello World";
  }
}
