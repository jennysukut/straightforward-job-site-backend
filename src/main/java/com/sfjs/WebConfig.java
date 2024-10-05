package com.sfjs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Autowired
  private Environment env;

  private String allowedOrigins;

  @PostConstruct
  public void init() {
    allowedOrigins = env.getProperty("spring.webflux.cors.allowed-origins");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/graphql/**")
      .allowedOriginPatterns(allowedOrigins)
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
      .allowedHeaders("*")
      .allowCredentials(true)
      .maxAge(3600);
  }
}
