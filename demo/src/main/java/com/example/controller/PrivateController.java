package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("private/")
public class PrivateController {

	@RequestMapping(value = "service", method = RequestMethod.POST)
	public String service(){
		return "Test private ";
	}
}
