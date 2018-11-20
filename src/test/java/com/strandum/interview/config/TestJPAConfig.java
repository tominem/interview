package com.strandum.interview.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.strandum.interview.InterviewMain;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.strandum.interview")
@ComponentScan(basePackages = { "com.strandum.interview" }, 
		excludeFilters=@Filter(type=FilterType.ASSIGNABLE_TYPE, value=InterviewMain.class))
@PropertySource("classpath:application-test.properties")
@Profile("test")
public class TestJPAConfig {
	
	@Value("classpath:rest-response1.txt")
	private Resource restResponse1;

	@Value("classpath:rest-response2.txt")
	private Resource restResponse2;
	
	@Bean
	@Profile("test")
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
	            .generateUniqueName(false)
	            .setName("testdb")
	            .setType(EmbeddedDatabaseType.H2)
	            .setScriptEncoding("UTF-8")
	            .build();
	}
	
	@Bean
	@Profile("test")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource,
			@Value("${hibernate.jdbc.batch_size}") String batchSize) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.jdbc.batch_size", "1000");
		jpaProperties.setProperty("hibernate.order_inserts", "true");
		jpaProperties.setProperty("hibernate.order_updates", "true");
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		jpaProperties.put("hibernate.show_sql", "true");
		// jpaProperties.put("hibernate.format_sql", "true");
		entityManagerFactory.setJpaProperties(jpaProperties);
		entityManagerFactory.setPackagesToScan("com.strandum.interview.domain");
		entityManagerFactory
				.setPersistenceProvider(new HibernatePersistenceProvider());
		return entityManagerFactory;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	@Profile("test")
	public JpaTransactionManager transactionManager(DataSource dataSource,
			EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(
				entityManagerFactory);
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}
	
	@Bean
	public String restResponse1() throws IOException {
		try (InputStream is = restResponse1.getInputStream()) {
			return new String(Files.readAllBytes(restResponse1.getFile().toPath()));
		}
	}
	
	@Bean
	public String restResponse2() throws IOException {
		try (InputStream is = restResponse2.getInputStream()) {
			return new String(Files.readAllBytes(restResponse2.getFile().toPath()));
		}
	}

}
