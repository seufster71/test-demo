package com.example.configuration;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DataSourceMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("mydataSourcesApplication")
	private Map<String, DataSource> mydataSourcesApplication;
	
	@Override
	protected DataSource selectAnyDataSource() {
		logger.info("get any datasource "+mydataSourcesApplication.size());
		
		return this.mydataSourcesApplication.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		logger.info("get data source "+ tenantIdentifier);
		return this.mydataSourcesApplication.get(tenantIdentifier);
	}

}
