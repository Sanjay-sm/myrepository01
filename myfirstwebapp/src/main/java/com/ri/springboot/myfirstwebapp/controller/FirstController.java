package com.ri.springboot.myfirstwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class FirstController 
{
	  @RequestMapping("say-hello")
	  @ResponseBody
      public String sayHello() {
    	  return "hello.! what you are learning today ?";
      }
	  @RequestMapping("say-hello-html")
	  @ResponseBody
	  public String sayHelloHtml() {
		 StringBuffer sb = new StringBuffer();
		 sb.append("<html>");
		 sb.append("<head>");
		 sb.append("<title> my first web page </title>");
		 sb.append("</head>");
		 sb.append("<body>");
		 sb.append("my first html page with body");
		 sb.append("</body>");
		 sb.append("</html>");
		 return sb.toString();
	  }
	 
	  @RequestMapping("say-hello-jsp")
	   public String sayHelloJsp() 
	  {
    	  return "sayHello";
      }
}
