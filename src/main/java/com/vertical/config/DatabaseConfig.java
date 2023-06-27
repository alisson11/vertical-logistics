package com.vertical.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "entityManagerFactory",
		basePackages = {"com.logistics"})
public class DatabaseConfig {

	@Autowired private Environment env;

	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create()
				.driverClassName( env.getProperty( "spring.datasource.driver-class-name" ) )
				.url( env.getProperty( "spring.datasource.url" ) )
				.username( env.getProperty( "spring.datasource.username" ) )
				.password( env.getProperty( "spring.datasource.password" ) )
				.build();
	}
}
