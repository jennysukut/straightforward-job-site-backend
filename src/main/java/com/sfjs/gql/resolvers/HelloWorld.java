package com.sfjs.gql.resolvers;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class HelloWorld {

  @QueryMapping(name = "helloWorld")
  public String helloWorld() {
    return "Hello World";
  }
}
