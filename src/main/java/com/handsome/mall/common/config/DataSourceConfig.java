package com.handsome.mall.common.config;


import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

  @Primary
  @Bean(name = "primaryDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.primary")
  public DataSource primaryDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "historyDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.history")
  public DataSource secondaryDataSource() {
    return DataSourceBuilder.create().build();
  }
}

