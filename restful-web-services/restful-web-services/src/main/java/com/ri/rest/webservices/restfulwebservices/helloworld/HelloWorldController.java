package com.ri.rest.webservices.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	private MessageSource messageSource;
	

	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	

	@GetMapping(path = "/hello")
	public String helloWorld() {
		return "Hello World..!!";
	}
	

	@GetMapping(path = "/basicauth")
	public String basicAuth() {
		return "Success";
	}
	
	

	@GetMapping(path = "/hello-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World..!!");
	}
	
	@GetMapping(path = "/hello-bean/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World , %s", name));
	}
	
	@GetMapping(path = "/hello-bean-int")
	public String helloWorlIntn() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
		//return "Hello World..v2";
	}



}
