package com.ri.springsmsapp.controller;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import  org.springframework.http.MediaType;

import com.ri.springsmsapp.entity.SmsClass;
import com.ri.springsmsapp.service.SmsService;

@RestController
public class SmsController {
 private SmsService service;
 private SimpMessagingTemplate webSocket;
 private final String TOPIC_DESTINATION  = "/lesson/sms";
 
public SmsController(SmsService service, SimpMessagingTemplate webSocket) {
	this.service = service;
	this.webSocket = webSocket;
}

@RequestMapping(value="/sms", method = RequestMethod.POST, consumes = MediaType.
APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public void smsSubmit(@RequestBody SmsClass sms) {
	service.send(sms);
	webSocket.convertAndSend(TOPIC_DESTINATION,getTimeStamp()+": SMS has been sent!: "+ sms.getTo());
}


@RequestMapping(value = "/smscallback", method = RequestMethod.POST, 
consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public void smsCallBack(@RequestBody MultiValueMap<String, String> map) {
   service.rescive(map);
   webSocket.convertAndSend(TOPIC_DESTINATION,getTimeStamp()+" : Twilio has made callback request!!");
}



private String getTimeStamp() {
	
	return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
}
 
}
