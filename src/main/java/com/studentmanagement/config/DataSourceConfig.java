package com.studentmanagement.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJpaRepositories
public class DataSourceConfig {

//    @Primary
//    @Bean(name = "studentDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource studentDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
////    @Bean
////    @ConfigurationProperties(prefix = "spring.datasource.jpa.entity")
////    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource studenDataSource) {
////        return builder
////                .dataSource(studenDataSource)
////                .build();
////    }
//
//    @Bean(name = "securityDataSource")
//    @ConfigurationProperties(prefix = "datasource.second")
//    public DataSource securityDataSource() {
//        return DataSourceBuilder.create().build();
//    }
}