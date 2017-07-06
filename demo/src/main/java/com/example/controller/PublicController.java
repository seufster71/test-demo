package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.utils.Response;

@RestController
@RequestMapping("api/public/")
public class PublicController {

	@RequestMapping(value = "service", method = RequestMethod.POST)
	public Response service(){
		Response response = new Response();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("service", "public");
		response.setParams(params);
		
		return response;
	}
}
