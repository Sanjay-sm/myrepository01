package com.ri.springsmsapp.service;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.ri.springsmsapp.entity.SmsClass;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {
	private final String accountSid = "ACca3bd84e6057b94bae8dcfa4d816d57c";
	private final String authToken = "83348935db3d476c65a79275c961e6e8";
	private final String fromNumber = "+17175029340";
 
 
	public void send(SmsClass sms) {
		Twilio.init(accountSid, authToken );
		Message creator = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(fromNumber),
				sms.getMessage()).create();
		System.out.println("id:"+ creator.getAccountSid());
	}

	public void rescive(MultiValueMap<String, String> map) {
	}
	
	
  
}
