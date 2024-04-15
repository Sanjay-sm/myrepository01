package com.springphonewebapp.springphonebookapp.contacts;

import java.util.Arrays;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import net.bytebuddy.agent.builder.AgentBuilder.FallbackStrategy.Simple;

@Service
public class EmailService 
{
  private JavaMailSender mailSender;
  private ContactRepository repository;
  private SimpleMailMessage message;

public EmailService(JavaMailSender mailSender,ContactRepository repository) 
{
	this.mailSender = mailSender;
	this.repository = repository;
}

  public void sendEmail(Contacts contact) 
  {
	  message  = new SimpleMailMessage();
	  message.setFrom("sanjaykumarsp480@gmail.com");
	  message.setTo(contact.getEmail());
	  message.setSubject("Saving Contact");
	  message.setText("Your Contact has been saved Successfully Thank you..!!");
	  mailSender.send(message);
	  
  }
  
  //@Scheduled(cron = "00 50 10 * * *")
  public void remainderMail() 
  {
	  String[] listEmails = repository.findAllEmail();
	  message  = new SimpleMailMessage();
	  message.setFrom("sanjaykumarsp480@gmail.com");
	  message.setTo(listEmails);
	  message.setSubject("Remainder Mail");
	  message.setText("This is Remainder Mail");
	  mailSender.send(message);
	  System.out.println("remainder mail called");
	 
 }
  
}
