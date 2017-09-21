package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;

@RestController
@PreAuthorize("hasAuthority('PRIVATE')")
@RequestMapping("/api/private/")
public class PrivateController {

	@RequestMapping(value = "service", method = RequestMethod.POST)
	public Response service(@RequestBody Request request){
		
		Response response = new Response();
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("authstatus","in private");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		params.put("principal", auth.getPrincipal());
		response.setParams(params);
		
		return response;
	}
	
}
