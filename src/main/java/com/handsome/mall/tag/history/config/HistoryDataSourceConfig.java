package com.handsome.mall.tag.history.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class HistoryDataSourceConfig {

  @Bean(name = "historySqlSessionFactory")
  public SqlSessionFactory secondarySqlSessionFactory(
      @Qualifier("secondaryDataSource") DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    return sessionFactory.getObject();
  }


}
