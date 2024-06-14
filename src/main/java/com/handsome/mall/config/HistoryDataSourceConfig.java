package com.handsome.mall.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.handsome.mall.repository.history",  // History datasource repositories
    entityManagerFactoryRef = "historyEntityManagerFactory",
    transactionManagerRef = "historyTransactionManager"
)
public class HistoryDataSourceConfig {

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
            .packages("com.handsome.mall.entity.history")  // History datasource entities
            .persistenceUnit("history")
            .build();
    }

    @Bean(name = "historyTransactionManager")
    public PlatformTransactionManager historyTransactionManager(
        @Qualifier("historyEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
