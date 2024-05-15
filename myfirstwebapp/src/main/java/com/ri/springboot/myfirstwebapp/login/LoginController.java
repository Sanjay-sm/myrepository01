package com.ri.springboot.myfirstwebapp.login;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;

@Controller
public class LoginController 
{
	@Autowired
	private AuthenticationService authenticateService;
	private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	  @RequestMapping(value = "login-jsp", method = RequestMethod.GET)
	   public String gotoLoginPage() 
	   {
  	     return "login";
       } 

	  @RequestMapping(value = "welcome-jsp", method = RequestMethod.GET)
	   public String welcomePage() 
	   {
  	     return "welcome";
       } 
	  
	  @RequestMapping(value = "invalid-login", method = RequestMethod.POST)
	   public String invalidLoginPage(@RequestParam String username, @RequestParam String password) 
	   {

		  boolean authenticate = authenticateService.authenticate(username, password);
 	    
 	       if(authenticate == true) {
 	    	   return "redirect:list-todo";
 	       }
 	       
 	       else {
 	    	   return "invalidLogin";
 	       }
      } 
	  
	  @RequestMapping(value = "login-jsp", method = RequestMethod.POST)
	   public String authentication(@RequestParam String username, @RequestParam String password) 
	   {
		  boolean authenticate = authenticateService.authenticate(username, password);
 	    
 	       if(authenticate == true) {
 	    	   return "redirect:list-todo";
 	       }
 	       
 	       else {
 	    	   return "invalidLogin";
 	       }
 	     
      } 
	  
	  
}
