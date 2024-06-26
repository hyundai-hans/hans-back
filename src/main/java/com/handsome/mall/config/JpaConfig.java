package com.handsome.mall.config;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This class for set the default EntityFactoryBuilder by jpa vendor
 * @see org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
 */
@Configuration
public class JpaConfig {

  @Bean
  public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
    return new EntityManagerFactoryBuilder(
        new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter(),
        new java.util.HashMap<>(),
        null
    );
  }
}
