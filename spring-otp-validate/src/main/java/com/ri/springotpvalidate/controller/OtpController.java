package com.ri.springotpvalidate.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ri.springotpvalidate.service.EmailService;
import com.ri.springotpvalidate.service.OTPService;
import com.ri.springotpvalidate.template.EmailTemplate;

import jakarta.mail.MessagingException;

@RestController
public class OtpController {
 private OTPService otpService;
 private EmailService emailService;
public OtpController(OTPService otpService, EmailService emailService) {
	this.otpService = otpService;
	this.emailService = emailService;
}

@GetMapping("/generateOTP")
public String generateOTP() throws MessagingException{
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = auth.getName();
		int otp = otpService.generateOTP(username);
		
		//Generate The Template to send OTP 
		EmailTemplate template = new EmailTemplate("SendOtp.html");
		
		Map<String,String> replacements = new HashMap<String,String>();
		replacements.put("user", username);
		replacements.put("otpnum", String.valueOf(otp));
		String message = template.getTemplate(replacements);
		emailService.sendOtpMessage("sanjaysm963@gmail.com", "OTP -SpringBoot", message);
		
return "otppage";
}
@RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
public @ResponseBody String validateOtp(@RequestParam("otpnum") int otpnum){
	
		final String SUCCESS = "Entered Otp is valid";
		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = auth.getName();
		//Validate the Otp 
		if(otpnum >= 0){
			
		  int serverOtp = otpService.getOtp(username);
		    if(serverOtp > 0){
		      if(otpnum == serverOtp){
		          otpService.clearOTP(username);
		
                  return ("Entered Otp is valid");
                } 
		        else {
                    return SUCCESS;
                   }
               }else {
              return FAIL;
               }
             }else {
                return FAIL;
         }
      }
}
