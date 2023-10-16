package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class JsonController{
	private static final String GREETING = "Hello, %s!";
	
	@GetMapping("/json")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws JsonProcessingException {
		
		String greeting = String.format(GREETING, name);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(greeting);
		
		return json;
	}
}