package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/")
public class PublicController {

	@RequestMapping(value = "service", method = RequestMethod.POST)
	public String service(){
		return "Test public ";
	}
}
