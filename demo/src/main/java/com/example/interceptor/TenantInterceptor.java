package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.configuration.TenantContext;

public class TenantInterceptor extends HandlerInterceptorAdapter {

	private static final String TENANT_HEADER_NAME = "X-TENANT-ID";
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		TenantContext.setTenantId(null);
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String tenantId = request.getHeader(TENANT_HEADER_NAME);
		if (tenantId == null) {
			tenantId = "tenant_1";
		}
		TenantContext.setTenantId(tenantId);
		return super.preHandle(request, response, handler);
	}

}
