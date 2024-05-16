package com.ri.springotpvalidate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	private JavaMailSender mailSender;
	
	public EmailService(JavaMailSender mailSender) {
		
		this.mailSender = mailSender;
		
	}
	
	public void sendOtpMessage(String to, String subject,String textMessage) throws MessagingException {
		  MimeMessage msg = mailSender.createMimeMessage();
		  MimeMessageHelper helper = new MimeMessageHelper(msg,true);
		  helper.setTo(to);
		  helper.setSubject(subject);
		  helper.setText(textMessage,true);
		  mailSender.send(msg);
		  
}

}
