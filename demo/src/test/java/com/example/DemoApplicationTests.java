package com.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.utils.Request;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	
	@Autowired
	private TestRestTemplate restTemplate;
	private int port = 8090;
	HttpHeaders headers = new HttpHeaders();
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void loginInToRestPost() {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("username", "test");
		params.put("password", "test");
		Request request = new Request();
		request.setParams(params);
		
		
		HttpEntity<Request> entity = new HttpEntity<Request>(request, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/login/service"),
				HttpMethod.POST, entity, String.class);

		String expected = "{params:{username:test,password:test}}";

		try {
			JSONAssert.assertEquals(expected, response.getBody(), false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	 
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
