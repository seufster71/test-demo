package com.example.configuration;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static String DEFAULT_TENANT_ID = "tenant_1";
	
	@Override
	public String resolveCurrentTenantIdentifier() {
		String currentTenantId = TenantContext.getTenantId();
		logger.info("resolver "+ currentTenantId);
		String x = (currentTenantId != null) ? currentTenantId : DEFAULT_TENANT_ID;
		logger.info("resolver item "+ x);
		return x;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		// TODO Auto-generated method stub
		return false;
	}

}
