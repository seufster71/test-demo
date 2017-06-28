package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.utils.Request;
import com.example.utils.Response;

@RestController
@RequestMapping("login/")
public class LoginController {

	@RequestMapping(value = "service", method = RequestMethod.POST)
	public Response service(@RequestBody Request request){
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("username", request.getParams().get("username"));
		params.put("password", request.getParams().get("password"));
		Response response = new Response();
		response.setParams(params);
		
		return response;
	}
}
