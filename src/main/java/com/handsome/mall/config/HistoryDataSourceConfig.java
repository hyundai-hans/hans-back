package com.handsome.mall.config;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@RequiredArgsConstructor
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.handsome.mall.repository.history",
    entityManagerFactoryRef = "historyEntityManagerFactory",
    transactionManagerRef = "historyTransactionManager"
)
public class HistoryDataSourceConfig {


    @Value("${hibernate.history.ddl-auto}")
    String ddlSetting;

    @Bean(name = "historyDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.history")
    public DataSource historyDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "historyEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean historyEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("historyDataSource") DataSource dataSource) {

        return builder
            .dataSource(dataSource)
            .packages("com.handsome.mall.entity.history")
            .properties(getProperties())
            .persistenceUnit("history")
            .build();
    }

    @Bean(name = "historyTransactionManager")
    public PlatformTransactionManager historyTransactionManager(
        @Qualifier("historyEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

private Map<String, Object> getProperties() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", ddlSetting);
    properties.put("hibernate.show_sql", "true");

    return properties;
}
}
