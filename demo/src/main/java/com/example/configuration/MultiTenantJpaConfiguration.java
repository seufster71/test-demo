package com.example.configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.configuration.MultiTenantProperties.Datasource;

@Configuration
@EnableConfigurationProperties({ MultiTenantProperties.class })
@PropertySource(value = { "classpath:application.properties" })
@EnableTransactionManagement
public class MultiTenantJpaConfiguration {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MultiTenantProperties multiTenantProperties;
	
	@Autowired
    private Environment environment;
	
	@Bean(name = "mydataSourcesApplication" )
	public Map<String, DataSource> mydataSourcesApplication() {
		logger.info("dataSourcesApplication -- loop through datasources");
		Map<String, DataSource> result = new HashMap<>();
		for (Datasource dsProperties : multiTenantProperties.getDatasources()) {
			DataSourceBuilder factory = DataSourceBuilder
				.create()
				.url(dsProperties.getUrl())
				.username(dsProperties.getUsername())
				.password(dsProperties.getPassword())
				.driverClassName(dsProperties.getDriverClassName());
			result.put(dsProperties.getTenantId(), factory.build());
		}
		return result;
	}
	
	
	@Bean
	public MultiTenantConnectionProvider multiTenantConnectionProvider() {
		return new DataSourceMultiTenantConnectionProviderImpl();
	}
	
	@Bean
	public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
		return new TenantIdentifierResolverImpl();
	}
	
	@Bean(name = "multiEntityManagerFactoryBean")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(MultiTenantConnectionProvider multiTenantConnectionProvider,
		CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {
		logger.info("entityManagerFactoryBean called");
		Map<String, Object> hibernateProps = new LinkedHashMap<>();
		//hibernateProps.putAll(hibernateProperties());
		hibernateProps.put(org.hibernate.cfg.Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
		hibernateProps.put(org.hibernate.cfg.Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
		hibernateProps.put(org.hibernate.cfg.Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
		hibernateProps.put(org.hibernate.cfg.Environment.DIALECT, environment.getRequiredProperty("hibernate.multitenant.dialect"));
		hibernateProps.put(org.hibernate.cfg.Environment.SHOW_SQL, environment.getRequiredProperty("hibernate.multitenant.show_sql"));
		hibernateProps.put(org.hibernate.cfg.Environment.DIALECT, environment.getRequiredProperty("hibernate.multitenant.dialect"));
		
		// No dataSource is set to resulting entityManagerFactoryBean
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPackagesToScan(new String[] {"com.example.core.multi.model"});
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaPropertyMap(hibernateProps);
		em.setPersistenceUnitName("PU3");
		
		return em;
	}
	
	@Bean(name = "entityManagerFactoryMulti")
	public EntityManagerFactory entityManagerFactory(@Qualifier("multiEntityManagerFactoryBean")LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return entityManagerFactoryBean.getObject();
	}


	@Bean(name = "transactionManagerMulti")
	public PlatformTransactionManager transactionManagerMulti(@Qualifier("entityManagerFactoryMulti") EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
}
