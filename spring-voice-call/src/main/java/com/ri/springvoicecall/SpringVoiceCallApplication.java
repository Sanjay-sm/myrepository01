package com.ri.springvoicecall;

import java.net.URI;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

@SpringBootApplication
public class SpringVoiceCallApplication implements ApplicationRunner {

	private static String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
	private static String AUTH_ID = System.getenv("TWILIO_AUTH_TOKEN");
	private static String FROM_NUM = "+13203019692";
	private static String TO_NUM = "+919611682895";

	static {
		Twilio.init(ACCOUNT_SID, AUTH_ID);
	}

	
	public static void main(String[] args) {
		SpringApplication.run(SpringVoiceCallApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Call call = Call.creator(new PhoneNumber(TO_NUM), new PhoneNumber(FROM_NUM),
				new URI("http://demo.twilio.com/docs/voice.xml")).create();
		System.out.println(call.getSid());

	}

}
