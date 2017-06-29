package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.utils.Request;
import com.example.utils.Response;

@RestController
@PreAuthorize("hasAuthority('METRIC')")
@RequestMapping("metric/")
public class MetricController {

	@RequestMapping(value = "service", method = RequestMethod.POST)
	public Response service(@RequestBody Request request){
		
		Response response = new Response();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("authstatus","in metric");
		response.setParams(params);
		
		return response;
	}
}
