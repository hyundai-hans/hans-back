package com.handsome.mall.tag.history.config;

import com.handsome.mall.common.annotation.HistoryDB;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.handsome.mall.tag", annotationClass = HistoryDB.class, sqlSessionTemplateRef = "historySqlSessionTemplate")
public class HistoryDataSourceConfig {


  @Bean(name = "historySqlSessionFactory")
  public SqlSessionFactory historySqlSessionFactory(
      @Qualifier("historyDataSource") DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    return sessionFactory.getObject();
  }

  @Bean(name = "historyTransactionManager")
  public DataSourceTransactionManager historyTransactionManager(
      @Qualifier("historyDataSource") DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean(name = "historySqlSessionTemplate")
  public SqlSessionTemplate historySqlSessionTemplate(
      @Qualifier("historySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
