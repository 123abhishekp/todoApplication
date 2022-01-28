package com.abis.app.todoApplication.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class HelloWorldController {

	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/helloWorld")
	public String helloWorld(){

		return "Hello World";
	}
	
	
	@GetMapping(path="/helloWorldBean")
	public HelloWorldBean helloWorldBean(){

		return new HelloWorldBean("Hello World Bean");
	}
	
	
	@GetMapping(path="/helloWorld-Internationalization")
	public String helloWorldInternan(@RequestHeader(name="Accept-Language",required = false) Locale locale){

		return messageSource.getMessage("customer.text",null, locale);
	}
}
